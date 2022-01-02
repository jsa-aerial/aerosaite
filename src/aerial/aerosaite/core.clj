(ns aerial.aerosaite.core

  (:gen-class)

  (:require [clojure.string :as cljstr]
            [clojure.java.io :as io]
            [clojure.data.csv :as csv]
            [clojure.data.json :as json]

            [clj-http.client :as httpc]
            [me.raynes.fs.compression :as cmp]

            ;; Command line arg processing
            [clojure.tools.cli :refer [parse-opts]]

            ;; Tunneling Cider nREPL support
            [clojure.tools.nrepl.server :as nrs]
            #_[cider.nrepl :refer [cider-middleware]] ; BROKEN SIDE EFFECTS
            [refactor-nrepl.middleware :refer [wrap-refactor]]

            [cpath-clj.core :as cp] ; Extract Jar resources


            #_[dynapath.defaults :as dd]
            #_[cemerick.pomegranate :as pom]

            [aerial.fs :as fs]
            [aerial.utils.io :as aio]
            [aerial.utils.coll :as coll]
            [aerial.utils.math :as m]
            [aerial.utils.math.probs-stats :as prob]

            [aerial.hanami.core :as hmi]
            [aerial.hanami.common :as hc :refer [RMV]]
            [aerial.hanami.templates :as ht]

            [aerial.saite.core :as as]))



(def cli-options

  [["-I" "--install" "Perform self installation"
    :default nil]

   ["-U" "--update" "Perform update of resources"
    :default nil]

   ["-p" "--port PORT" "Port number for http server"
    :default nil
    :parse-fn #(Integer/parseInt %)
    :validate [#(< 0 % 0x10000) "Must be a number between 0 and 65536"]]

   ["-r" "--repl-port PORT" "Port for nrepl server"
    :default nil
    :parse-fn #(Integer/parseInt %)
    :validate [#(< 4000 % 5000) "Must be a number between 4001 and 4999"]]

   ["-h" "--help" "Print this help"
    :default true]])




(def home-path (fs/join "~" ".saite"))

(defn- get-install-dir
  "Query user for location that will be saite installation and home
   directory
  "
  [update?]
  (if update?
    (if (-> home-path fs/fullpath fs/directory?)
      (fs/fullpath home-path)
      (do (print (format "Enter Saite home directory [%s]: " home-path))
          (flush)
          (let [input (read-line)
                input (fs/fullpath (if (= input "") home-path input))]
            (println)
            (println "Update location:" input)
            input)))
    (do (print (format "Enter installation directory [%s]: " home-path))
        (flush)
        (let [input (read-line)
              input (fs/fullpath (if (= input "") home-path input))]
          (println)
          (println "Selected installation location:" input)
          input))))


;;; "mkl-libs.tar.gz"
(defn get-mkl-libs [saitedir mkl-tar-gz]
  (let [tmpfile (fs/join saitedir "mkl-libs.tar")]
    (->
     (httpc/get
      (fs/join "http://bioinformatics.bc.edu/~jsa" mkl-tar-gz)
      {:as :byte-array})
     (:body)
     (io/input-stream)
     (java.util.zip.GZIPInputStream.)
     (clojure.java.io/copy (clojure.java.io/file tmpfile)))
    (cmp/untar tmpfile (fs/join saitedir "Libs"))
    (fs/rm tmpfile)))


(defn install-resource [saitedir res]
  (doseq [[path uris] (cp/resources (io/resource res))
          :let [uri (first uris) _ (prn :uri uri :path path)
                relative-path (if (not= path "") (subs path 1) path)
                fspec (if (= relative-path "")
                        (->> res (fs/join saitedir)
                             fs/fullpath)
                        (->> relative-path
                             (fs/join saitedir res)
                             fs/fullpath))
                output-dir (fs/dirname fspec)
                ;;_ (prn :FSPEC fspec)
                ;;_ (prn :OD output-dir)
                output-file (io/file fspec)]]
    (when (not (re-find #"^pack/" path)) ; bug in cp/resources regexer
      #_(println :PATH path)
      #_(println :RELATIVE-PATH relative-path)
      (when (not (fs/exists? output-dir))
        (fs/mkdirs output-dir))
      (println uri :->> output-file)
      (with-open [in (io/input-stream uri)]
        (io/copy in output-file))
      (when (= res "bin")
        (.setExecutable output-file true false)))))

(defn install-mkl [saitedir]
  (let [os (System/getProperty "os.name")
        os-mkl-map {"Linux" "linux-mkl-libs.tar.gz"
                    "Mac OS X" "mac-mkl-libs.tar.gz"
                    "Windows 10" "win-mkl-libs.tar.gz"}]
    (when (not (fs/exists? (fs/join saitedir "Libs")))
      (fs/mkdirs (fs/fullpath (fs/join saitedir "Libs"))))
    (if-let [file (os-mkl-map os)]
      (do (println "Installing MKL libraries - may take a minute or so...")
          (get-mkl-libs (fs/fullpath saitedir) file))
      (println (format "Unsupported OS '%s'. Supported OSes %s"
                       os (keys os-mkl-map))))))


(defn set-uberjar-cp [saitedir version]
  (let [runners (fs/glob (fs/join saitedir "*runserver"))]
    (doseq [f runners]
      (-> f slurp
          (cljstr/replace "SAITEDIR" saitedir)
          (cljstr/replace "VERSION" version)
          (->> (spit f))))))


(defn get-jar-path []
  (-> (class *ns*)
      .getProtectionDomain
      .getCodeSource
      .getLocation
      .toURI .getPath))

(defn move-jar-to-home [saitedir]
  (let [jar (get-jar-path)]
    (fs/copy jar (fs/join saitedir (fs/basename jar)))
    (fs/basename jar)))


(defn install-aerosaite
  "Create installation directory, mark it as the home directory and
   install required resources and set the host machine dns lookup name
   in websocket address. SAITEDIR is the directory user gave on query
   for location to install.
  "
  [saitedir]
  (println "Creating installation(saite home) directory...")
  (fs/mkdirs (fs/fullpath saitedir))
  (println "Marking installation directory as saite home")
  (spit (->> ".saite-home-dir" (fs/join saitedir) fs/fullpath)
        "Home directory of saite")

  (println "Installing resources...")
  (doseq [res ["Code" "Docs" "Data"
               "linux-runserver" "jvm11+-linux-runserver"
               "mac-runserver"   "jvm11+-mac-runserver"
               "config.edn"]]
    (install-resource saitedir res))

  ;; As of version 0.7+ aerosaite now pulls the public resources from
  ;; the uberjar and places them in ~/.saite/resources/public. This
  ;; enables users to create new resources under the web root (images,
  ;; data, etc).  In particular, Clojisr requires this for ggplot
  ;; things.
  (install-resource (fs/join saitedir "resources") "public")

  ;; Grab MKL libs for OS
  (try
    (install-mkl saitedir)
    (catch Exception e#
      (let [msg (str ((Throwable->map e#) :cause))]
        (println "MKL install failed: " msg))))

  ;; Move JAR to saite home directory and set the uberjar classpath
  (let [version (-> (move-jar-to-home saitedir) (cljstr/split #"-") second)]
    (set-uberjar-cp saitedir version))

  (println "\n\n*** Installation complete"))


(defn update-aerosaite
  "Update an installation of Aerosaite to latest versions of resources.
  Uses a manifest file to indicate the new versions and/or new
  resources."
  [saitedir]

  ;; We now, 0.7+, must _always_ update resources/public tree
  (let [date (-> java.util.Date new
                 (->> (.format (java.text.SimpleDateFormat. "MM/dd/yyyy")))
                 (cljstr/replace #"/" "-"))
        bkupdir (->> date (fs/join saitedir "BkUps") fs/fullpath)
        resourcesdir (->> "resources" (fs/join saitedir) fs/fullpath)]
    (println :BKUP bkupdir)
    (fs/mkdirs bkupdir)
    (fs/move (fs/join resourcesdir "public") bkupdir)
    (install-resource resourcesdir "public"))

  (when-let [manifest-url (io/resource "manifest.txt")]
    (let [lines (-> manifest-url slurp (cljstr/split #"\n"))
          version (first lines)
          resources (rest lines)
          reldir (->>  version (fs/join saitedir "Updates") fs/fullpath)]
      (println :VERSION version :RELDIR reldir)
      (when (not (fs/exists? reldir)) (fs/mkdirs reldir))
      (doseq [res resources]
        (cond
          (= res "MKL")
          (try
            (do (println "Updating MKL support...")
                (install-mkl reldir))
            (catch Exception e#
              (let [msg (str ((Throwable->map e#) :cause))]
                (println "MKL update failed: " msg))))

          (#{"linux-runserver" "jvm11+-linux-runserver"
             "mac-runserver"   "jvm11+-mac-runserver"
             "config.edn"} res)
          (let [old (str res "-old")]
            (when (fs/exists? (fs/join saitedir res))
              (fs/copy (fs/join saitedir res) (fs/join saitedir old)))
            (install-resource saitedir res))

          :else
          (install-resource reldir res)))

      (println (format "\n\n*** Updated resources in\n    '%s'" reldir))))

  ;; Move JAR to saite home directory and set the uberjar classpath
  (let [version (-> (move-jar-to-home saitedir) (cljstr/split #"-") second)]
    (set-uberjar-cp saitedir version))

  (println "\n\n*** Update complete.\n"))


(defn- find-set-home-dir
  "Tries to find the aerosaite home directory and, if not current working
   directory sets working directory to home directory.
  "
  []
  (let [curdir (fs/pwd)
        stdhm (fs/fullpath "~/.saite")]
    (cond
     (->> ".saite-home-dir" (fs/join curdir) fs/exists?) curdir

     (and (fs/exists? stdhm) (not= stdhm curdir)
          (->> ".saite-home-dir" (fs/join stdhm) fs/exists?))
     (do (fs/cd stdhm) stdhm)

     :else nil)))




(defn nrepl-handler-hack []
  (require 'cider.nrepl)
  (let [cm (var-get (ns-resolve 'cider.nrepl 'cider-middleware))
        ;;cm (filter #(not= % 'cider.nrepl/wrap-pprint-fn) cm)
        resolve-or-fail (fn[sym] (println :sym sym)
                          (or (ns-resolve 'cider.nrepl sym)
                              (throw (IllegalArgumentException.
                                      (format "Cannot resolve %s" sym)))))]
    (clojure.pprint/pprint cm)
    (apply nrs/default-handler
           (concat (mapv resolve-or-fail cm)
                   [#'wrap-refactor]))))


(defn -main
  "Daemon starter ...."
  [& args]

  (let [opts (parse-opts args cli-options)
        options (opts :options)
        arguments (opts :arguments)
        summary (opts :summary)
        errors (opts :errors)
        http-port (options :port)
        rpl-port (options :repl-port)
        install? (options :install)
        update? (options :update)
        nrepl-handler (nrepl-handler-hack)]
    (if errors
      (do (println "Error(s): " errors)
          (System/exit 1))
      (cond
        install?
        (let [saitedir (get-install-dir false)]
          (if (fs/exists? saitedir)
            (do (println "Selected install dir already exists!")
                (System/exit 1))
            (install-aerosaite saitedir))
          (System/exit 0))

        update?
        (let [saitedir (get-install-dir true)]
          (if (fs/exists? saitedir)
            (update-aerosaite saitedir)
            (do (println "Saite home directory does not exist - can't update!")
                (System/exit 1)))
          (System/exit 0))

        (and http-port rpl-port)
        (if (find-set-home-dir)
          (let [jar (get-jar-path)
                version (-> jar fs/basename (cljstr/split #"-") second
                            (->> (str "V")))]
            (println version :http-port http-port :rpl-port rpl-port)
            (nrs/start-server :port rpl-port :handler nrepl-handler)
            (.bindRoot Compiler/LOADER
                       (clojure.lang.DynamicClassLoader.
                        (clojure.lang.RT/baseLoader)))
            (println (str (Thread/currentThread))
                     (str (deref Compiler/LOADER)))
            (as/start http-port))
          (do
            (println "Cannot find std home directory '~/.saite'")
            (println "If installed, run server from installation directory")
            (println "Otherwise run installation first: `aerosaite --install'")
            (System/exit 1)))

        (options :help)
        (do
          (println summary)
          (System/exit 0))

        :else
        (do (println "Unknown options " options)
            (System/exit 1))))))


#_(as/start 3000)
#_(as/stop)


(comment
  (pom/add-dependencies
   :coordinates '[[uncomplicate/neanderthal "0.26.1"]
                  [criterium "0.4.4"]]
   :repositories
   (merge cemerick.pomegranate.aether/maven-central
          {"clojars" "https://clojars.org/repo"}))

  (require '[uncomplicate.commons.core
             :refer [with-release let-release
                     Releaseable release]])

  (require '[uncomplicate.neanderthal
             [native :refer [dv dge]]
             [core :refer [mv! mv axpy! scal! transfer!]]
             [math :refer [signum exp]]
             [vect-math :refer [fmax! tanh! linear-frac!]]])


  (pom/add-dependencies
   :coordinates '[[kixi/stats "0.5.2"]]
   :repositories
   (merge cemerick.pomegranate.aether/maven-central
          {"clojars" "https://clojars.org/repo"}))

  (require '[kixi.stats.core
             :as ks
             :refer [standard-deviation correlation]])
  nil
  (->> [{:x 2} {:x 4} {:x 4} {:x 4} {:x 5} {:x 5} {:x 5} {:x 7} {:x 9}]
       (transduce (map :x) standard-deviation))
  2.0


  )

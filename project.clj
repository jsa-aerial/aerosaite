(defproject aerial.aerosaite "1.6.9"
  :description
  "Saite for work with Aerobio and sequencing analysis and anything else!"
  :url "https://github.com/aerial/aerobio-saite"
  :license {:name "The MIT License (MIT)"
            :url  "http://opensource.org/licenses/MIT"
            :distribution :repo}

  :min-lein-version "2.3.3"
  :global-vars {*warn-on-reflection* false
                *assert* true}

  :dependencies [[org.clojure/clojure        "1.12.1"]
                 [org.clojure/java.classpath "0.3.0"]
                 [org.clojure/tools.reader   "1.3.6"]
                 [nrepl                      "1.3.0"] ; Explicit nREPL
                 [org.clojure/tools.cli      "1.0.206"]

                 [aerial.saite "1.6.9"]
                 [clj-http     "3.12.3"] ; http client, downloads
                 [cpath-clj    "0.1.2"]  ; JAR resources access
                 ]

  :plugins [[cider/cider-nrepl "0.50.2"]
            ;[refactor-nrepl    "3.10.0"]
            ]

  :profiles {:uberjar {:aot :all}}

  :manifest {"Class-Path" "~/.saite/resources"}
  :main aerial.aerosaite.core)

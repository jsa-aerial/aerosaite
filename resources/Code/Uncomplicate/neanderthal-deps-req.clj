
;;; All of this is must run on the JVM.  So, use Ctrl-X J (with cursor at
;;; end right paren) or Ctrl-X Ctrl-J with curso inside a form

;;; Dependencies and resource requires and imports
;;;
(deps '[[uncomplicate/neanderthal "0.43.3"]
        [criterium "0.4.4"]])

;;; Apple is terrible.  They constantly are breaking things so we need this
;;; now for Macs
#_(deps '[[uncomplicate/neanderthal "0.43.1"
         :exclusions 
         [[org.jcuda/jcuda-natives :classifier "apple-x86_64"]
          [org.jcuda/jcublas-natives :classifier "apple-x86_64"]]]
        [criterium "0.4.4"]])


(require '[uncomplicate.commons.core
           :refer [with-release let-release
                   Releaseable release]]
         '[uncomplicate.fluokitten.core :refer [fmap!]]
         '[uncomplicate.neanderthal
           [native :refer [dv dge fge dtr native-float]]
           [core :refer [copy copy! mv! mv axpy! scal! transfer! submatrix
                         transfer! transfer mrows ncols nrm2 mm cols view-tr]]
           [real :refer [entry entry!]]
           [math :refer [signum exp]]
           [linalg :refer [trf tri det]]
           [vect-math :refer [fmax! tanh! linear-frac!]]])

(import clojure.lang.IFn)



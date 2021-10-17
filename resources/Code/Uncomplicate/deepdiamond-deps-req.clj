





;;; All of this is must run on the JVM.  So, use Ctrl-X J (with cursor at
;;; end right paren) or Ctrl-X Ctrl-J with curso inside a form

(deps '[[uncomplicate/neanderthal "0.43.3"]
        [uncomplicate/deep-diamond "0.21.0"]
        [criterium "0.4.4"]])


;;; Apple is terrible.  They constantly are breaking things so we need this
;;; now for Macs
#_(deps '[[uncomplicate/neanderthal "0.43.3"
         :exclusions 
         [[org.jcuda/jcuda-natives :classifier "apple-x86_64"]
          [org.jcuda/jcublas-natives :classifier "apple-x86_64"]]]
        [uncomplicate/deep-diamond "0.21.0"
         :exclusions
         [[org.jcuda/jcudnn-natives :classifier "apple-x86_64"]]]
        [criterium "0.4.4"]])



(require '[uncomplicate.commons.core
           :refer [with-release let-release
                   Releaseable release]]
         '[uncomplicate.fluokitten.core :refer [fmap!]]
         '[uncomplicate.neanderthal
           [native :refer [fv dv dge fge dtr native-float]]
           [core :refer [copy copy! mv! mv axpy! scal! transfer! submatrix dot
                         amax
                         transfer! transfer mrows ncols nrm2 mm cols
                         view-tr view-vctr
                         row asum axpy rk subvector trans]]
           [random :refer [rand-normal! rand-uniform! rng-state]]
           [block :refer [buffer contiguous?]]
           [real :refer [entry entry!]]
           [math :refer [signum exp]]
           [linalg :refer [trf tri det]]
           [vect-math :refer [fmax! tanh! mul linear-frac!]]]
         '[uncomplicate.diamond.tensor :as dt]
         '[uncomplicate.diamond.native :as dnat])

(let [t (dt/tensor 3)] (prn t))

(def t1 (dt/tensor 3))
(dt/shape t1)
(dt/layout t1)
(dt/data-type t1)

(def v1 (fv 3))
(= t1 v1)

t1str


(entry! t1 0 100)
(def t1-view (view-vctr t1))
(entry! t1-view 0 100)
(map type [t1 v1 t1-view])


(do (entry! t1-view 100) :done)
(do (rand-normal! t1-view) :done)
(amax t1)



(def t2 (dt/tensor [3 2] :float :nc))
(dt/shape t2)
(dt/layout t2)



























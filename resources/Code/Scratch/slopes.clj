




(def ptselect
  (-> {:mark :point
       :encoding :ENCODING
       :params [{:name :pname> :select :pselect>}]}
    (assoc :aerial.hanami.templates/defaults
           {:OPACITY {:condition {:param :pname> :empty :empty> :value 1}
                      :value 0}
            :ENCODING {:opacity :OPACITY}
            :pname> "pselect" :empty> false
            :pselect> {:type :point :encodings :encodings>
                       :nearest :nearest> :on :mouseover}
            :encodings> ["x"] :nearest> true
            })))

(hc/xform ptselect)

(def slope-rule
  {:mark (merge ht/mark-base {:type "rule"})
   :transform [{:filter {:param :pname>, :empty false}}]
   :encoding (assoc ht/xy-encoding
                    :y2 {:field :Y2 :type :Y2TYPE :bin :Y2BIN
                         :axis :Y2AXIS :scale :Y2SCALE :sort :Y2SORT
                          :aggregate :Y2AGG}
                    :x2 {:field :X2 :type :X2TYPE :bin :X2BIN
                         :axis :X2AXIS :scale :X2SCALE :sort :X2SORT
                         :aggregate :X2AGG})
   :aerial.hanami.templates/defaults
   {:MCOLOR "red"
    :XSCALE {:zero false} :YSCALE {:zero false}
    :X2SCALE {:zero false} :Y2SCALE {:zero false}
    :X2 :x2 :X2TYPE :quantitative :Y2 :y :Y2TYPE :quantitative
    :X2BIN hc/RMV :X2AXIS hc/RMV :X2SORT hc/RMV :X2AGG hc/RMV
    :Y2BIN hc/RMV :Y2AXIS hc/RMV :Y2SORT hc/RMV :Y2AGG hc/RMV}})





(defn roundit [r & {:keys [places] :or {places 4}}]
  (let [n (Math/pow 10.0 places)]
    (-> r (* n) Math/round (/ n))))

(defn slope-at
  "Estimate the slope of the function f at the point x
  using accuracy delt"
  [f delt x]
  (/ (- (f (+ x delt)) (f (- x delt))) (* 2 delt)))

(defn x**2 [x] (* x x))

(defn lfn [x a b] (+ (* a x) b))

;;; y = ax + b
(def testdata
  (mapv (fn[x]
          (let [delta 0.01
                dd (* 50 delta)
                a (slope-at x**2 delta x)
                y (x**2 x)
                x1 (- x dd)
                x2 (+ x dd)
                b (- (* a x) y)
                offset (- (lfn x a b) y)
                y1 (- (lfn x1 a b) offset)
                y2 (- (lfn x2 a b) offset)]
            (mapv roundit [x y x1 y1 x2 y2 a])))
        (range 0 3 0.1)))











(let [data (mapv (fn[[x y x1 y1 x2 y2 a]]
                   {:x x :y y :slope a
                    :x1 x1 :y1 y1 :x2 x2 :y2 y2 :ftype "f"})
                 testdata)]
  (hc/xform
   ht/layer-chart
   :FID :f1 :VID :v1
   :DATA data
   :COLOR "ftype" :pname> "slope"
   :WIDTH 600 :HEIGHT 600
   :TOOLTIP [:XTTIP :YTTIP {:field :slope}]
   :LAYER [{:encoding :ENCODING
            :layer [{:mark {:type "line"}}
                    ptselect]}
           (hc/xform
            slope-rule
            :DATA data
            :X :x1 :Y :y1 :X2 :x2 :Y2 :y2
            :TOOLTIP [:XTTIP :YTTIP {:field :x2} {:field :y2}
                      {:field :slope}])]))

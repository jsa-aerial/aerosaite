


(count (range -100.0 100.0 0.5))

(take 10 (range -100.0 100.0 0.5))
(take-last 10 (range -100.0 100.0 0.5))



(def dataset
  (->>
  (range -100.0 100.0 0.5)
  (mapv #(vector % %))
  (mapv (fn[[x y]] {:x x :y y}))))




;;; A simple Vega-Lite spec for bar chart visualization
;;;{
;;;  "data": {"values": []},
;;;  "mark": "line",
;;;  "encoding": {
;;;    "x": {"field": "x", "type": "quantitative"},
;;;    "y": {"field": "y", "type": "quantitative"}
;;;  }
;;;}








(def chart
  {:usermeta (hc/get-default :USERDATA)
   :data {:values dataset},
   :mark "line",
   :encoding
   {:x {:field "x", :type "quantitative"},
    :y {:field "y", :type "quantitative"}}})










(-> (hc/xform
     chart :TID :charts :FID :f1 :VID :v1)
  hmi/sv!)






(hc/xform
 chart :TID :charts :FID :f1 :VID :v1)







(hc/xform
 ht/line-chart :FID :f2 :VID :v2
 :DATA dataset)

;;; change data to x^2
;;; (mapv (fn[m] (assoc m :y (let [x (m :x)] (* x x))))dataset)
;;; Add :TITLE "y = x^2"
;;; Add :YTITLE "x^2"
;;; Add :TOP `[[md " \\(f(x) = x^2\\)"]]
;;; Add {:style {:font-size "16px"}}
;;; Change :TOP to :BOTTOM
;;; Add [gap :size "220px"] component
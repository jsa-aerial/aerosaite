


(def residual-rule
  {:mark (merge ht/mark-base {:type "rule"})
   :encoding (assoc ht/xy-encoding
                    :y2 {:field :Y2 :type :Y2TYPE :bin :Y2BIN
                         :axis :Y2AXIS :scale :Y2SCALE :sort :Y2SORT
                         :aggregate :Y2AGG})
   :aerial.hanami.templates/defaults
   {:MCOLOR "red"
    :XSCALE {:zero false} :YSCALE {:zero false} :Y2SCALE {:zero false}
    :Y2 :prediction :Y2TYPE :quantitative
    :Y2BIN hc/RMV :Y2AXIS hc/RMV :Y2SORT hc/RMV :Y2AGG hc/RMV}})

(def residual-plot-chart
  (assoc ht/layer-chart
         :aerial.hanami.templates/defaults
         {:XSCALE {:zero false} :YSCALE {:zero false}
          :RESIDUAL hc/RMV
          :LINE hc/RMV
          :POINTS ht/point-layer
          :STANDARD (hc/xform ht/line-chart :DATA :REG-DATA :MCOLOR "black")}
         :encoding ht/xy-encoding
         :layer [:RESIDUAL :POINTS :LINE]))








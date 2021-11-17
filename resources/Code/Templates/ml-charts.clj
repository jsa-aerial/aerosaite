

;;; Rule for drawing lines between prediction and residual
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

;;; Chart for data and residual plots
;;; Three layers (optional for each): raw points, prediction line, residuals
;;; Parameters :POINTS point layer, defaults to ht/point-layer
;;;            :LINE prediction line layer, defaults to remove
;;;            :RESIDUAL residual point layer, defaults to remove
;;;            :REG-DATA regression line data
;;; Use :LINE :STANDARD for standard regression line
;;;     :RESIDUAL :residual-rule for residual marks (line segments)
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




;;; Base layer for trend-chart
(def trend-layer
  (assoc ht/line-chart
         :aerial.hanami.templates/defaults
         {:X :data/x> :XTYPE :xtype>
          :Y :data/y> :YTYPE :ytype>
          :YSCALE {:zero false}
          :DATA hc/RMV
          :WIDTH :width> :HEIGHT :height>
          :USERDATA hc/RMV}))

;;; Trend chart - two layers.  1. base data, 2. loess trend line
;;; Parameters :data/x for x-axis data. :data/y> for y-axis data
;;;            :xtype> and :ytype> for axis types, will default to :XTYPE :YTYPE
;;;            :width> and :height>, will default to :WIDTH and :HEIGHT
;;;            :trend-color> for loess line, defaults to "firebrick"
(def trend-chart
  (assoc ht/layer-chart
         :description ""
         :aerial.hanami.templates/defaults
         {:LAYER [(hc/xform trend-layer)
                  (hc/xform
                   trend-layer
                   :TRANSFORM [{:loess :data/y> :on :data/x>}]
                   :MCOLOR :trend-color>)]
          :trend-color> "firebrick"
          :xtype> :XTYPE :ytype> :YTYPE
          :width> 700 :height> (hc/get-defaults :HEIGHT)}))






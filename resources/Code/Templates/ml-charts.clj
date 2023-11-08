

;;; Heat maps
;;; Originally in hanami.templates but now most current versions here

(def heatmap-chart
  "Base heatmap chart
  Two layers - ht/rect-layer and ht/text-layer"
  (assoc layer-chart
         :encoding xy-encoding
         :layer [ht/rect-layer ht/text-layer]
         :aerial.hanami.templates/defaults {:MTOOLTIP RMV}))

(def corr-heatmap
  "specialize heatmap-chart for correlation heatmaps
  Parameters :xcol> x-axis values
             :ycol> y-axis values
             :corr> correlation field
             :sortx> sort x values by see VL sort property (defaults to RMV)
             :sorty> sort y values by see VL sort property (defaults to RMV)
             :colorscale> defaults to {:scheme \"redgrey\" :reverse true}
                          another nice one:
                          {:scheme \"greenblue\" :reverse true}
             :test> VL condition test used to flip color of correlation text
                    value from 'black' to 'white'.  For example:
                    (format \"datum['%s'] < %s\" \"count\" 100)
             "
  (assoc heatmap-chart
         :aerial.hanami.templates/defaults
         {:X :colx>, :XSORT :sortx> :XTYPE RMV
          :Y :coly>, :YSORT :sorty> :YTYPE RMV
          :sortx> RMV :sorty RMV
          :COLOR ht/default-color
          :CFIELD :corr> :CTYPE "quantitative" :CSCALE :colorscale>
          :TXT :corr> :TTYPE "quantitative"
          :TCOLOR {:value "black"
                   :condition {:test :test> :value "white"}}
          :colorscale> {:scheme "redgrey" :reverse true}}))




;;; Rule for drawing lines between prediction and residual
(def residual-rule
  "Rule for drawing lines between prediction and residual
  One parameter `:prediction` for rule's Y encoding."
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
;;; Uses above residual-rule in its definition
(def residual-plot-chart
  "Chart for data and residual plots
  Three layers (optional for each): raw points, prediction line, residuals
  Parameters :POINTS point layer, defaults to ht/point-layer
             :LINE prediction line layer, defaults to remove
             :RESIDUAL residual point layer, defaults to remove
             :REG-DATA regression line data
  Use :LINE :STANDARD for standard regression line
      :RESIDUAL residual-rule for residual marks (line segments)"
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
(def trend-chart
  "A two layer plot for base data and its smoothed trend line given by
  loess transform
  Parameters :data/x for x-axis data. :data/y> for y-axis data
             :xtype> and :ytype> for axis types, will default to :XTYPE :YTYPE
             :loessbw> LOESS bandwidth (determines data window) in (0,1)
                       defaults to 0.3
             :width>, will default to 700
             :height>, will default to :HEIGHT default
             :trend-color> for loess line, defaults to 'firebrick'"
  (assoc ht/layer-chart
         :description
         :aerial.hanami.templates/defaults
         {:LAYER [(hc/xform trend-layer)
                  (hc/xform
                   trend-layer
                   :TRANSFORM [{:loess :data/y> :on :data/x>
                                :bandwidth :loessbw>}]
                   :MCOLOR :trend-color>)]
          :trend-color> "firebrick"
          :xtype> :XTYPE :ytype> :YTYPE
          :loessbw> 0.3
          :width> 700
          :height> (hc/get-defaults :HEIGHT)}))





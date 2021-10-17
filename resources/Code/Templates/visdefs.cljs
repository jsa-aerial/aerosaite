

:cljs


(def slider-hist
  (-> ht/view-base
    (assoc :mark (merge ht/mark-base {:type "bar"})
           :encoding :BINNED-ENCODING
           :usermeta
           (xframes
            {:TOP (xc top :defaults? false
                      :components [markdown])
             :LEFT (xc sidebar :defaults? false
                       :components [sliderInput textInput])}))))


(def layer-hist
  (-> ht/layer-chart
    (assoc :usermeta (xframes {:TOP top :LEFT sidebar})
           :layer [(hc/xform
                    ht/bar-chart :WIDTH 700
                    :XTITLE "Duration (in minutes)"
                    :ENCODING :BINNED-ENCODING :Y :Frequency
                    :DATA :bin-data)
                   (hc/xform
                    ht/line-chart :WIDTH 700 :MCOLOR "red"
                    :DATA :den-data)]
           :resolve{:scale {"y" "independent" "x" "independent"}})))




(let [mdwn-brush
      (hc/xform ht/interval-brush-mdwn :MDWM-NAME "brush" :IRESOLVE "global")
      color {:field "NM" :type "nominal"
             :scale {:range ["#e45756" "#54a24b" "#4c78a8"]}}
      size {:condition {:selection {:not "brush"} :value 40} :value 400}
      tooltip `[{:field "Gene", :type "nominal"}
                ~@ht/default-tooltip
                {:field "pvalue", :type "quantitative"}]]
  (def dge-chart
    (-> ht/hconcat-chart
      (assoc :hconcat
             [(hc/xform
               ht/point-chart :WIDTH 500 :HEIGHT 400
               :TITLE "MA Plot"
               :MSIZE 40
               :SELECTION (merge  (hc/xform ht/interval-scales :INAME "grid1")
                                  mdwn-brush)
               :X "baseMean", :Y "log2FoldChange"
               :COLOR color, :SIZE size, :TOOLTIP tooltip)
              (hc/xform
               ht/point-chart :WIDTH 500 :HEIGHT 400
               :TITLE "Volcano Plot"
               :MSIZE 40
               :SELECTION (merge (hc/xform ht/interval-scales :INAME "grid2")
                                 mdwn-brush)
               :X "log2FoldChange", :Y "-log10pval"
               :COLOR color, :SIZE size :TOOLTIP tooltip)]))))







;;; Not a very good template - needs more and better abstraction...
(def geo-choro
  (assoc ht/view-base
         :mark {:type "geoshape" :stroke :STROKE}
         :encoding {:shape {:field "geo", :type "geojson"},
                    :color {:field :X, :type "quantitative" :scale :YSCALE}
                    :tooltip :TOOLTIP}
         :projection :PROJECTION))



(def us-10m (str vgdata-base "us-10m.json"))

(def cov2-US-geo-choro
  (-> geo-choro
    (assoc :usermeta
           (xframes
            {:TOP (xc top :defaults? false :components [markdown])
             :RIGHT (xc sidebar :defaults? false :components :rcomponents)
             :LEFT  (xc sidebar :defaults? false :components :lcomponents)})
           :transform
           [{:lookup "id",
             :from {:data {:url us-10m
                           :format {:type "topojson" :feature "states"}}
                    :key "id"}
             :as "geo"}])))

(def cov2-daily-states
  (-> ht/layer-chart
    (assoc :usermeta
           (xframes
            {:TOP (xc top :defaults? false :components [markdown])
             :RIGHT (xc sidebar :defaults? false :components :rcomponents)
             :LEFT  (xc sidebar :defaults? false :components :lcomponents)}))))




(hc/update-defaults
 :GTTFIELD "name" :GTTTYPE "nominal"
 :PROJECTION {:type "albersUsa"}
 :STROKE hc/RMV
 :GEOENCODING
 {:longitude {:field "longitude", :type "quantitative"},
  :latitude {:field "latitude", :type "quantitative"},
  :tooltip [{:field :GTTFIELD, :type :GTTTYPE}
            {:field "longitude", :type "quantitative"}
            {:field "latitude", :type "quantitative"}],
  :size :SIZE})


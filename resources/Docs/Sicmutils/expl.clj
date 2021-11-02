[{:ed3 {:label "Deps/Req", :opts {:order :row, :eltsper 1, :size "auto", :wrapfn {:tid :ed3, :$split nil, :out-width "1300px", :fn [quote editor-repl-tab], :layout :left-right, :ns smu.code, :ed-out-order :first-last, :width "730px", :md-defaults nil, :src "\n(deps '[[sicmutils \"0.19.2\"]])\n\n(require '[sicmutils.env\n           :as env\n           :refer [D sin cos square cube compose ->TeX]])\n\n(env/bootstrap-repl!)\n", :out-height "900px", :eid "ed-ed3", :height "900px"}}, :specs []}} {:ed4 {:label "Code", :opts {:order :row, :eltsper 1, :size "auto", :wrapfn {:tid :ed4, :$split nil, :out-width "1300px", :fn [quote editor-repl-tab], :layout :left-right, :ns smu.code, :ed-out-order :first-last, :width "730px", :md-defaults nil, :src "\n\n(-> 'x cube ->TeX)\n((D cube) 5)\n\n(let [f (env/+ sin cos)]\n  (->TeX ((D f) 'x)))\n\n;;;(-> 'x sin square ->TeX ((partial format \"\\\\(%s\\\\)\")))\n\n(defn probfn [x]\n  (env/+ (square x) (cube x)))\n\n(let [f (env/+ (square sin) cos)\n      df (D f)\n      pfn probfn\n      dpfn (D pfn)]\n  (-> 'x dpfn env/simplify\n    ->TeX ((partial format \"\\\\(%s\\\\)\"))))\n\n(mapv probfn (range 10))\n((D probfn) 4)\n(mapv (D probfn) (range 10))\n\n\n\n\n\n(pr-str '(defn mjtex [v f & {:keys [simp?] :or {simp? true}}]\n  (let [simpfn (if simp? env/simplify identity)]\n    (-> v f simpfn\n      ->TeX ((partial format \"\\\\(%s\\\\)\"))))))\n\n(def src1\n\"\n(defn mjtex [v f & {:keys [simp?], :or {simp? true}}]\n  (let [simpfn (if simp? env/simplify identity)]\n    (-> v f simpfn\n      ->TeX ((partial format \\\"\\\\\\\\(%s\\\\\\\\)\\\")))))\n\n;; Try it out\n(mjtex 'x cube)\n(mjtex 'x (square sin))\n(mjtex 'y (env/+ sin cos))\n\n;; Note, the default simplify makes this just 1\n(mjtex 'x (env/+ (square sin) (square cos)))\n(mjtex 'x (env/+ (square sin) (square cos)) :simp? false)\n\")\n\n(def src2\n\"\n(defn get-data [f values]\n  (let [df (D f)\n        fx (mjtex 'x f)\n        dfx (mjtex 'x df)\n        fvalues (mapv f values)\n        dfvalues (mapv df values)]\n    [fx dfx values fvalues dfvalues]))\n\n;; Try it out\n(get-data cube (range 5))\n(defn afn [x] (env/+ (square x) (cube x)))\n(get-data afn (range 10))\n\")\n\n\n(pr-str\n '(let [[ftex dftex values fvalues dfvalues]\n      (clj (get-data probfn (range 10)))]\n  (let [fvpairs (->> fvalues (interleave (repeat \"f\")) (partition-all 2))\n        dfvpairs (->> dfvalues (interleave (repeat \"f'\")) (partition-all 2))]\n    (hc/xform\n     ht/point-chart\n     :FID :f3 :VID :v1 :TXT (str \"f = \" ftex \"\\n f' = \" dftex)\n     :DATA (mapv (fn[x [ftype y]] {:x x :y y :ftype ftype})\n                 (concat values values) (concat fvpairs dfvpairs))\n     :HEIGHT 500 :WIDTH 600 :MSIZE 80 :COLOR \"ftype\"\n     :LEFT '[[p {:style {:width \"50px\" :min-width \"50px\"}}]]\n     :RIGHT '[[gap :size \"20px\"]\n            [md {:style {:font-size \"16px\" :width \"800px\"}} :TXT]\n            ]))))\n\n(def src3\n\"\n(let [[ftex dftex values fvalues dfvalues]\n      (clj (get-data probfn (range 10)))]\n  (hmi/visualize\n   (let [fvpairs (->> fvalues (interleave (repeat \\\"f\\\"))\n                      (partition-all 2))\n         dfvpairs (->> dfvalues (interleave (repeat \\\"f'\\\"))\n                       (partition-all 2))]\n     (hc/xform\n      ht/point-chart\n      :FID :f3 :VID :fdf1\n      :TXT (str \\\"f = \\\" ftex \\\"\\\\n f' = \\\" dftex)\n      :DATA (mapv (fn [x [ftype y]] {:x x, :y y, :ftype ftype})\n                  (concat values values)\n                  (concat fvpairs dfvpairs))\n      :HEIGHT 500 :WIDTH 600 :MSIZE 80 :COLOR \\\"ftype\\\"\n      :LEFT '[[p {:style {:width \\\"50px\\\", :min-width \\\"50px\\\"}}]]\n      :RIGHT '[[gap :size \\\"20px\\\"]\n               [md {:style {:font-size \\\"16px\\\", :width \\\"800px\\\"}}\n                   :TXT]]))\n   (js/document.getElementById \\\"fdf1\\\")))\"\n)\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n:end", :out-height "900px", :eid "ed-ed4", :height "900px"}}, :specs []}} {:chap3 {:label "Vis", :opts {:order :row, :eltsper 1, :rgap "20px", :cgap "20px", :size "auto", :wrapfn {:tid :chap3, :$split 39.306640625, :out-width "730px", :fn [quote interactive-doc-tab], :cmfids {:vis 1, :cm 0, :fm 4}, :ns smu.code, :ed-out-order :first-last, :width "730px", :md-defaults nil, :src "\n\n(defn mjtex [v f]\n  (-> v f env/simplify\n    ->TeX ((partial format \"\\\\(%s\\\\)\"))))\n\n(defn get-data [f values]\n  (let [df (D f)\n        fx (mjtex 'x f)\n        dfx (mjtex 'x df)\n        fvalues (mapv f values)\n        dfvalues (mapv df values)]\n    [fx dfx values fvalues dfvalues]))\n\n\n(def slope-rule\n  {:mark (merge ht/mark-base {:type \"rule\"})\n   :encoding (assoc ht/xy-encoding\n                    :y2 {:field :Y2 :type :Y2TYPE :bin :Y2BIN\n                         :axis :Y2AXIS :scale :Y2SCALE :sort :Y2SORT\n                          :aggregate :Y2AGG}\n                    :x2 {:field :X2 :type :X2TYPE :bin :X2BIN\n                         :axis :X2AXIS :scale :X2SCALE :sort :X2SORT\n                         :aggregate :X2AGG})\n   :aerial.hanami.templates/defaults\n   {:MCOLOR \"red\"\n    :XSCALE {:zero false} :YSCALE {:zero false}\n    :X2SCALE {:zero false} :Y2SCALE {:zero false}\n    :X2 :x2 :X2TYPE :quantitative :Y2 :y :Y2TYPE :quantitative\n    :X2BIN hc/RMV :X2AXIS hc/RMV :X2SORT hc/RMV :X2AGG hc/RMV\n    :Y2BIN hc/RMV :Y2AXIS hc/RMV :Y2SORT hc/RMV :Y2AGG hc/RMV}})\n\n\n\n(let [[ftex dftex values fvalues dfvalues]\n      (clj (get-data probfn (range 10)))]\n  (let [fvpairs (->> fvalues (interleave (repeat \"f\")) (partition-all 2))\n        dfvpairs (->> dfvalues (interleave (repeat \"f'\")) (partition-all 2))]\n    (hc/xform\n     ht/point-chart\n     :FID :f3 :VID :v1 :TXT (str \"f = \" ftex \"\\n f' = \" dftex)\n     :DATA (mapv (fn[x [ftype y]] {:x x :y y :ftype ftype})\n                 (concat values values) (concat fvpairs dfvpairs))\n     :HEIGHT 500 :WIDTH 600 :MSIZE 80 :COLOR \"ftype\"\n     :LEFT '[[p {:style {:width \"50px\" :min-width \"50px\"}}]]\n     :RIGHT '[[gap :size \"20px\"]\n            [md {:style {:font-size \"16px\" :width \"800px\"}} :TXT]\n            ])))\n\n\n\n\n(let [[ftex dftex values fvalues dfvalues]\n      (clj (get-data probfn (range 0 0.5 0.01)))]\n  (let [fvpairs (->> fvalues (interleave (repeat \"f\"))\n                  (partition-all 2))\n        dfvpairs (->> dfvalues (interleave (repeat \"f'\"))\n                   (partition-all 2))]\n    (hc/xform\n     ht/layer-chart\n     :FID :f4 :VID :v4 :TXT (str \"f = \" ftex \"\\n f' = \" dftex)\n     :DATA (mapv (fn[x [ftype y]] {:x x :y y :ftype ftype})\n                 (concat values values)\n                 (concat fvpairs dfvpairs))\n     :LAYER (mapv #(hc/xform \n                    ht/point-chart\n                    :HEIGHT 500 :WIDTH 600 :MSIZE 80 :COLOR \"ftype\"\n                    :TRANSFORM [{:filter {:field \"ftype\" :equal %}}])\n                  [\"f\" \"f'\"])\n     :RESOLVE {:scale {\"y\" \"independent\"}}\n     :LEFT '[[p {:style {:width \"50px\" :min-width \"50px\"}}]]\n     :RIGHT '[[gap :size \"20px\"]\n            [md {:style {:font-size \"16px\" :width \"800px\"}} :TXT]\n            ])))\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n:none\n\n(let [[ftex dftex values fvalues dfvalues]\n      (clj (get-data probfn (range 10)))]\n  (let [fvpairs (->> fvalues (interleave (repeat :f)) (partition-all 2))\n        dfvpairs (->> dfvalues (interleave (repeat :df)) (partition-all 2))]\n    (mapv (fn[x [ftype y]] {:x x :y y :ftype ftype})\n          (concat values values) (concat fvpairs dfvpairs))))\n\n(hc/xform\n ht/bar-chart :FID :f4 :VID :v4\n :DATA [{:a \"A\", :b 28} {:a \"B\", :b 55} {:a \"C\", :b 43}\n        {:a \"D\", :b 91} {:a \"E\", :b 81} {:a \"F\", :b 53}\n        {:a \"G\", :b 19} {:a \"H\", :b 87} {:a \"I\", :b 52}]\n :X :a :XTYPE :nominal :Y :b)\n\n\n\n\n\n:end", :out-height "100px", :eid "ed-chap3", :height "790px"}}, :specs [{:usermeta {:frame {:pos :after, :at :end, :right [[gap :size "20px"] [md {:style {:font-size "16px", :width "800px"}} "f = \\({x}^{3} + {x}^{2}\\)\n f' = \\(3\\,{x}^{2} + 2\\,x\\)"]], :fid :f4, :left [[p {:style {:min-width "50px", :width "50px"}}]]}, :vid :v4, :session-name "Exploring", :opts {:mode "vega-lite", :source false, :export {:png true, :svg true}, :renderer "canvas", :scaleFactor 1, :editor true}, :msgop :tabs, :tab {:label "Chap3", :id :chap3, :opts {:eltsper 2, :size "auto", :rgap "20px", :cgap "20px", :order :row}}}, :width 400, :background "floralwhite", :layer [{:encoding {:y {:field "y", :type "quantitative"}, :color {:field "ftype", :type "nominal"}, :x {:field "x", :type "quantitative"}, :tooltip [{:field "x", :type "quantitative"} {:field "y", :type "quantitative"}]}, :transform [{:filter {:field "ftype", :equal "f"}}], :usermeta {:frame {:pos :after, :at :end}, :session-name "Exploring", :opts {:mode "vega-lite", :source false, :export {:png true, :svg true}, :renderer "canvas", :scaleFactor 1, :editor true}, :msgop :tabs, :tab {:label "Chap3", :id :chap3, :opts {:eltsper 2, :size "auto", :rgap "20px", :cgap "20px", :order :row}}}, :mark {:type "circle", :size 80}, :width 600, :background "floralwhite", :height 500} {:encoding {:y {:field "y", :type "quantitative"}, :color {:field "ftype", :type "nominal"}, :x {:field "x", :type "quantitative"}, :tooltip [{:field "x", :type "quantitative"} {:field "y", :type "quantitative"}]}, :transform [{:filter {:field "ftype", :equal "f'"}}], :usermeta {:frame {:pos :after, :at :end}, :session-name "Exploring", :opts {:mode "vega-lite", :source false, :export {:png true, :svg true}, :renderer "canvas", :scaleFactor 1, :editor true}, :msgop :tabs, :tab {:label "Chap3", :id :chap3, :opts {:eltsper 2, :size "auto", :rgap "20px", :cgap "20px", :order :row}}}, :mark {:type "circle", :size 80}, :width 600, :background "floralwhite", :height 500}], :resolve {:scale {"y" "independent"}}, :height 300, :data {:values [{:y 0, :ftype "f", :x 0} {:y 1.01E-4, :ftype "f", :x 0.01} {:y 4.08E-4, :ftype "f", :x 0.02} {:y 9.27E-4, :ftype "f", :x 0.03} {:y 0.0016640000000000001, :ftype "f", :x 0.04} {:y 0.0026250000000000006, :ftype "f", :x 0.05} {:y 0.003816000000000001, :ftype "f", :x 0.060000000000000005} {:y 0.005243000000000001, :ftype "f", :x 0.07} {:y 0.006912000000000001, :ftype "f", :x 0.08} {:y 0.008829, :ftype "f", :x 0.09} {:y 0.010999999999999998, :ftype "f", :x 0.09999999999999999} {:y 0.013430999999999997, :ftype "f", :x 0.10999999999999999} {:y 0.016127999999999997, :ftype "f", :x 0.11999999999999998} {:y 0.019096999999999992, :ftype "f", :x 0.12999999999999998} {:y 0.022343999999999996, :ftype "f", :x 0.13999999999999999} {:y 0.025875, :ftype "f", :x 0.15} {:y 0.029696, :ftype "f", :x 0.16} {:y 0.03381300000000001, :ftype "f", :x 0.17} {:y 0.03823200000000001, :ftype "f", :x 0.18000000000000002} {:y 0.04295900000000002, :ftype "f", :x 0.19000000000000003} {:y 0.04800000000000002, :ftype "f", :x 0.20000000000000004} {:y 0.053361000000000026, :ftype "f", :x 0.21000000000000005} {:y 0.05904800000000003, :ftype "f", :x 0.22000000000000006} {:y 0.06506700000000004, :ftype "f", :x 0.23000000000000007} {:y 0.07142400000000004, :ftype "f", :x 0.24000000000000007} {:y 0.07812500000000004, :ftype "f", :x 0.25000000000000006} {:y 0.08517600000000004, :ftype "f", :x 0.26000000000000006} {:y 0.09258300000000005, :ftype "f", :x 0.2700000000000001} {:y 0.10035200000000005, :ftype "f", :x 0.2800000000000001} {:y 0.10848900000000007, :ftype "f", :x 0.2900000000000001} {:y 0.11700000000000009, :ftype "f", :x 0.3000000000000001} {:y 0.12589100000000011, :ftype "f", :x 0.3100000000000001} {:y 0.13516800000000012, :ftype "f", :x 0.3200000000000001} {:y 0.1448370000000001, :ftype "f", :x 0.3300000000000001} {:y 0.15490400000000015, :ftype "f", :x 0.34000000000000014} {:y 0.16537500000000016, :ftype "f", :x 0.35000000000000014} {:y 0.17625600000000016, :ftype "f", :x 0.36000000000000015} {:y 0.1875530000000002, :ftype "f", :x 0.37000000000000016} {:y 0.19927200000000023, :ftype "f", :x 0.38000000000000017} {:y 0.21141900000000025, :ftype "f", :x 0.3900000000000002} {:y 0.22400000000000023, :ftype "f", :x 0.4000000000000002} {:y 0.23702100000000026, :ftype "f", :x 0.4100000000000002} {:y 0.25048800000000027, :ftype "f", :x 0.4200000000000002} {:y 0.2644070000000003, :ftype "f", :x 0.4300000000000002} {:y 0.2787840000000003, :ftype "f", :x 0.4400000000000002} {:y 0.29362500000000036, :ftype "f", :x 0.45000000000000023} {:y 0.3089360000000004, :ftype "f", :x 0.46000000000000024} {:y 0.32472300000000043, :ftype "f", :x 0.47000000000000025} {:y 0.3409920000000004, :ftype "f", :x 0.48000000000000026} {:y 0.35774900000000043, :ftype "f", :x 0.49000000000000027} {:y 0, :ftype "f'", :x 0} {:y 0.020300000000000002, :ftype "f'", :x 0.01} {:y 0.0412, :ftype "f'", :x 0.02} {:y 0.06269999999999999, :ftype "f'", :x 0.03} {:y 0.0848, :ftype "f'", :x 0.04} {:y 0.10750000000000001, :ftype "f'", :x 0.05} {:y 0.1308, :ftype "f'", :x 0.060000000000000005} {:y 0.1547, :ftype "f'", :x 0.07} {:y 0.1792, :ftype "f'", :x 0.08} {:y 0.20429999999999998, :ftype "f'", :x 0.09} {:y 0.22999999999999998, :ftype "f'", :x 0.09999999999999999} {:y 0.2563, :ftype "f'", :x 0.10999999999999999} {:y 0.28319999999999995, :ftype "f'", :x 0.11999999999999998} {:y 0.3106999999999999, :ftype "f'", :x 0.12999999999999998} {:y 0.3388, :ftype "f'", :x 0.13999999999999999} {:y 0.3675, :ftype "f'", :x 0.15} {:y 0.39680000000000004, :ftype "f'", :x 0.16} {:y 0.4267, :ftype "f'", :x 0.17} {:y 0.45720000000000005, :ftype "f'", :x 0.18000000000000002} {:y 0.48830000000000007, :ftype "f'", :x 0.19000000000000003} {:y 0.5200000000000001, :ftype "f'", :x 0.20000000000000004} {:y 0.5523000000000001, :ftype "f'", :x 0.21000000000000005} {:y 0.5852000000000002, :ftype "f'", :x 0.22000000000000006} {:y 0.6187000000000002, :ftype "f'", :x 0.23000000000000007} {:y 0.6528000000000003, :ftype "f'", :x 0.24000000000000007} {:y 0.6875000000000002, :ftype "f'", :x 0.25000000000000006} {:y 0.7228000000000002, :ftype "f'", :x 0.26000000000000006} {:y 0.7587000000000003, :ftype "f'", :x 0.2700000000000001} {:y 0.7952000000000004, :ftype "f'", :x 0.2800000000000001} {:y 0.8323000000000003, :ftype "f'", :x 0.2900000000000001} {:y 0.8700000000000003, :ftype "f'", :x 0.3000000000000001} {:y 0.9083000000000004, :ftype "f'", :x 0.3100000000000001} {:y 0.9472000000000005, :ftype "f'", :x 0.3200000000000001} {:y 0.9867000000000005, :ftype "f'", :x 0.3300000000000001} {:y 1.0268000000000006, :ftype "f'", :x 0.34000000000000014} {:y 1.0675000000000006, :ftype "f'", :x 0.35000000000000014} {:y 1.1088000000000007, :ftype "f'", :x 0.36000000000000015} {:y 1.1507000000000007, :ftype "f'", :x 0.37000000000000016} {:y 1.1932000000000007, :ftype "f'", :x 0.38000000000000017} {:y 1.2363000000000008, :ftype "f'", :x 0.3900000000000002} {:y 1.2800000000000007, :ftype "f'", :x 0.4000000000000002} {:y 1.324300000000001, :ftype "f'", :x 0.4100000000000002} {:y 1.369200000000001, :ftype "f'", :x 0.4200000000000002} {:y 1.414700000000001, :ftype "f'", :x 0.4300000000000002} {:y 1.460800000000001, :ftype "f'", :x 0.4400000000000002} {:y 1.5075000000000012, :ftype "f'", :x 0.45000000000000023} {:y 1.554800000000001, :ftype "f'", :x 0.46000000000000024} {:y 1.6027000000000013, :ftype "f'", :x 0.47000000000000025} {:y 1.6512000000000011, :ftype "f'", :x 0.48000000000000026} {:y 1.7003000000000013, :ftype "f'", :x 0.49000000000000027}]}}]}} {:chap5 {:label "Vis2", :opts {:order :row, :eltsper 1, :rgap "20px", :cgap "20px", :size "auto", :wrapfn {:tid :chap5, :$split 0, :out-width "730px", :fn [quote interactive-doc-tab], :cmfids {:cm 0, :fm 0}, :ns smu.code, :ed-out-order :first-last, :width "730px", :md-defaults {:cm {:out-width "500px", :ed-out-order :first-last, :width "600px"}}, :src "\n(sc/set-md-defaults\n {:cm {:width \"600px\"   ; All these are same as config default - chg as needed\n       ;;:height \"30px\"\n       :out-width \"500px\"\n       ;; :out-height \"0px\"\n       :ed-out-order :first-last}})\n\n\n\n(hc/xform\n ht/empty-chart\n :FID :f1 :SRC src1\n :LEFT '[[p {:style {:width \"50px\" :min-width \"50px\"}}]]\n :TOP '[[gap :size \"50px\"]\n        [v-box :gap \"5px\"\n         :children\n         [[md \"#### Define a couple of helper functions\"]\n          [md \"* Create a MathJax-ifier for Sicmutils TeX operator\"]\n          [cm :id \"e1\" :fid :FID :src :SRC\n           :readonly false :height \"280px\" :out-height \"150px\"\n           :layout :left-right]]]])\n\n(hc/xform\n ht/empty-chart\n :FID :f2 :SRC src2\n :LEFT '[[p {:style {:width \"50px\" :min-width \"50px\"}}]]\n :TOP '[[gap :size \"50px\"]\n        [v-box :gap \"5px\"\n         :children()\n         [[md \"* Compute data on server: f, f', f(values) f'(values)\"]\n          [cm :id \"e2\" :fid :FID :src :SRC\n           :readonly false :height \"280px\" :out-height \"150px\"\n           :layout :left-right]]]])\n\n\n(let [[ftex dftex values fvalues dfvalues]\n      (clj (get-data probfn (range 10)))]\n  (let [fvpairs (->> fvalues (interleave (repeat \"f\")) (partition-all 2))\n        dfvpairs (->> dfvalues (interleave (repeat \"f'\")) (partition-all 2))]\n    (hc/xform\n     ht/point-chart\n     :FID :f3 :VID :fdf1\n     :SRC src3 :TXT (str \"f = \" ftex \"\\n f' = \" dftex)\n     :DATA (mapv (fn [x [ftype y]] {:x x, :y y, :ftype ftype})\n                 (concat values values)\n                 (concat fvpairs dfvpairs))\n     :HEIGHT 500 :WIDTH 600 :MSIZE 80 :COLOR \"ftype\"\n     :TOP '[[gap :size \"50px\"]\n            [md \"#### Plot f, f'\"]]\n     :LEFT '[[gap :size \"50px\"]\n             [h-box :gap \"5px\"\n              :children\n              [[cm :id \"e3\" :fid :FID\n                :readonly false :vid :VID\n                :height \"400px\" :out-height \"150px\"\n                :out-width \"600px\" :src :SRC]\n               [md {:style {:width \"80px\" :min-width \"80px\"}} \"\"]]]]\n     :RIGHT '[[gap :size \"20px\"]\n              [md {:style {:font-size \"16px\" :width \"800px\"}} :TXT]])))\n\n\n\n(hmi/dbgon :md)\n\n\n\n\n", :out-height "100px", :eid "ed-chap5", :height "790px"}}, :specs [{:usermeta {:frame {:pos :after, :at :end, :right [[gap :size "20px"] [md {:style {:font-size "16px", :width "800px"}} "f = \\({x}^{3} + {x}^{2}\\)\n f' = \\(3\\,{x}^{2} + 2\\,x\\)"]], :fid :f4, :left [[p {:style {:min-width "50px", :width "50px"}}]]}, :vid :v4, :session-name "Exploring", :opts {:mode "vega-lite", :source false, :export {:png true, :svg true}, :renderer "canvas", :scaleFactor 1, :editor true}, :msgop :tabs, :tab {:label "Chap5", :id :chap5, :opts {:eltsper 2, :size "auto", :rgap "20px", :cgap "20px", :order :row}}}, :width 400, :background "floralwhite", :layer [{:encoding {:y {:field "y", :type "quantitative"}, :color {:field "ftype", :type "nominal"}, :x {:field "x", :type "quantitative"}, :tooltip [{:field "x", :type "quantitative"} {:field "y", :type "quantitative"}]}, :transform [{:filter {:field "ftype", :equal "f"}}], :usermeta {:frame {:pos :after, :at :end}, :session-name "Exploring", :opts {:mode "vega-lite", :source false, :export {:png true, :svg true}, :renderer "canvas", :scaleFactor 1, :editor true}, :msgop :tabs, :tab {:label "Chap5", :id :chap5, :opts {:eltsper 2, :size "auto", :rgap "20px", :cgap "20px", :order :row}}}, :mark {:type "circle", :size 80}, :width 600, :background "floralwhite", :height 500} {:encoding {:y {:field "y", :type "quantitative"}, :color {:field "ftype", :type "nominal"}, :x {:field "x", :type "quantitative"}, :tooltip [{:field "x", :type "quantitative"} {:field "y", :type "quantitative"}]}, :transform [{:filter {:field "ftype", :equal "f'"}}], :usermeta {:frame {:pos :after, :at :end}, :session-name "Exploring", :opts {:mode "vega-lite", :source false, :export {:png true, :svg true}, :renderer "canvas", :scaleFactor 1, :editor true}, :msgop :tabs, :tab {:label "Chap5", :id :chap5, :opts {:eltsper 2, :size "auto", :rgap "20px", :cgap "20px", :order :row}}}, :mark {:type "circle", :size 80}, :width 600, :background "floralwhite", :height 500}], :resolve {:scale {"y" "independent"}}, :height 300, :data {:values [{:y 0, :ftype "f", :x 0} {:y 1.01E-4, :ftype "f", :x 0.01} {:y 4.08E-4, :ftype "f", :x 0.02} {:y 9.27E-4, :ftype "f", :x 0.03} {:y 0.0016640000000000001, :ftype "f", :x 0.04} {:y 0.0026250000000000006, :ftype "f", :x 0.05} {:y 0.003816000000000001, :ftype "f", :x 0.060000000000000005} {:y 0.005243000000000001, :ftype "f", :x 0.07} {:y 0.006912000000000001, :ftype "f", :x 0.08} {:y 0.008829, :ftype "f", :x 0.09} {:y 0.010999999999999998, :ftype "f", :x 0.09999999999999999} {:y 0.013430999999999997, :ftype "f", :x 0.10999999999999999} {:y 0.016127999999999997, :ftype "f", :x 0.11999999999999998} {:y 0.019096999999999992, :ftype "f", :x 0.12999999999999998} {:y 0.022343999999999996, :ftype "f", :x 0.13999999999999999} {:y 0.025875, :ftype "f", :x 0.15} {:y 0.029696, :ftype "f", :x 0.16} {:y 0.03381300000000001, :ftype "f", :x 0.17} {:y 0.03823200000000001, :ftype "f", :x 0.18000000000000002} {:y 0.04295900000000002, :ftype "f", :x 0.19000000000000003} {:y 0.04800000000000002, :ftype "f", :x 0.20000000000000004} {:y 0.053361000000000026, :ftype "f", :x 0.21000000000000005} {:y 0.05904800000000003, :ftype "f", :x 0.22000000000000006} {:y 0.06506700000000004, :ftype "f", :x 0.23000000000000007} {:y 0.07142400000000004, :ftype "f", :x 0.24000000000000007} {:y 0.07812500000000004, :ftype "f", :x 0.25000000000000006} {:y 0.08517600000000004, :ftype "f", :x 0.26000000000000006} {:y 0.09258300000000005, :ftype "f", :x 0.2700000000000001} {:y 0.10035200000000005, :ftype "f", :x 0.2800000000000001} {:y 0.10848900000000007, :ftype "f", :x 0.2900000000000001} {:y 0.11700000000000009, :ftype "f", :x 0.3000000000000001} {:y 0.12589100000000011, :ftype "f", :x 0.3100000000000001} {:y 0.13516800000000012, :ftype "f", :x 0.3200000000000001} {:y 0.1448370000000001, :ftype "f", :x 0.3300000000000001} {:y 0.15490400000000015, :ftype "f", :x 0.34000000000000014} {:y 0.16537500000000016, :ftype "f", :x 0.35000000000000014} {:y 0.17625600000000016, :ftype "f", :x 0.36000000000000015} {:y 0.1875530000000002, :ftype "f", :x 0.37000000000000016} {:y 0.19927200000000023, :ftype "f", :x 0.38000000000000017} {:y 0.21141900000000025, :ftype "f", :x 0.3900000000000002} {:y 0.22400000000000023, :ftype "f", :x 0.4000000000000002} {:y 0.23702100000000026, :ftype "f", :x 0.4100000000000002} {:y 0.25048800000000027, :ftype "f", :x 0.4200000000000002} {:y 0.2644070000000003, :ftype "f", :x 0.4300000000000002} {:y 0.2787840000000003, :ftype "f", :x 0.4400000000000002} {:y 0.29362500000000036, :ftype "f", :x 0.45000000000000023} {:y 0.3089360000000004, :ftype "f", :x 0.46000000000000024} {:y 0.32472300000000043, :ftype "f", :x 0.47000000000000025} {:y 0.3409920000000004, :ftype "f", :x 0.48000000000000026} {:y 0.35774900000000043, :ftype "f", :x 0.49000000000000027} {:y 0, :ftype "f'", :x 0} {:y 0.020300000000000002, :ftype "f'", :x 0.01} {:y 0.0412, :ftype "f'", :x 0.02} {:y 0.06269999999999999, :ftype "f'", :x 0.03} {:y 0.0848, :ftype "f'", :x 0.04} {:y 0.10750000000000001, :ftype "f'", :x 0.05} {:y 0.1308, :ftype "f'", :x 0.060000000000000005} {:y 0.1547, :ftype "f'", :x 0.07} {:y 0.1792, :ftype "f'", :x 0.08} {:y 0.20429999999999998, :ftype "f'", :x 0.09} {:y 0.22999999999999998, :ftype "f'", :x 0.09999999999999999} {:y 0.2563, :ftype "f'", :x 0.10999999999999999} {:y 0.28319999999999995, :ftype "f'", :x 0.11999999999999998} {:y 0.3106999999999999, :ftype "f'", :x 0.12999999999999998} {:y 0.3388, :ftype "f'", :x 0.13999999999999999} {:y 0.3675, :ftype "f'", :x 0.15} {:y 0.39680000000000004, :ftype "f'", :x 0.16} {:y 0.4267, :ftype "f'", :x 0.17} {:y 0.45720000000000005, :ftype "f'", :x 0.18000000000000002} {:y 0.48830000000000007, :ftype "f'", :x 0.19000000000000003} {:y 0.5200000000000001, :ftype "f'", :x 0.20000000000000004} {:y 0.5523000000000001, :ftype "f'", :x 0.21000000000000005} {:y 0.5852000000000002, :ftype "f'", :x 0.22000000000000006} {:y 0.6187000000000002, :ftype "f'", :x 0.23000000000000007} {:y 0.6528000000000003, :ftype "f'", :x 0.24000000000000007} {:y 0.6875000000000002, :ftype "f'", :x 0.25000000000000006} {:y 0.7228000000000002, :ftype "f'", :x 0.26000000000000006} {:y 0.7587000000000003, :ftype "f'", :x 0.2700000000000001} {:y 0.7952000000000004, :ftype "f'", :x 0.2800000000000001} {:y 0.8323000000000003, :ftype "f'", :x 0.2900000000000001} {:y 0.8700000000000003, :ftype "f'", :x 0.3000000000000001} {:y 0.9083000000000004, :ftype "f'", :x 0.3100000000000001} {:y 0.9472000000000005, :ftype "f'", :x 0.3200000000000001} {:y 0.9867000000000005, :ftype "f'", :x 0.3300000000000001} {:y 1.0268000000000006, :ftype "f'", :x 0.34000000000000014} {:y 1.0675000000000006, :ftype "f'", :x 0.35000000000000014} {:y 1.1088000000000007, :ftype "f'", :x 0.36000000000000015} {:y 1.1507000000000007, :ftype "f'", :x 0.37000000000000016} {:y 1.1932000000000007, :ftype "f'", :x 0.38000000000000017} {:y 1.2363000000000008, :ftype "f'", :x 0.3900000000000002} {:y 1.2800000000000007, :ftype "f'", :x 0.4000000000000002} {:y 1.324300000000001, :ftype "f'", :x 0.4100000000000002} {:y 1.369200000000001, :ftype "f'", :x 0.4200000000000002} {:y 1.414700000000001, :ftype "f'", :x 0.4300000000000002} {:y 1.460800000000001, :ftype "f'", :x 0.4400000000000002} {:y 1.5075000000000012, :ftype "f'", :x 0.45000000000000023} {:y 1.554800000000001, :ftype "f'", :x 0.46000000000000024} {:y 1.6027000000000013, :ftype "f'", :x 0.47000000000000025} {:y 1.6512000000000011, :ftype "f'", :x 0.48000000000000026} {:y 1.7003000000000013, :ftype "f'", :x 0.49000000000000027}]}} {:usermeta {:frame {:top [[gap :size "50px"] [v-box :gap "5px" :children [[md "#### Define a couple of helper functions"] [md "* Create a MathJax-ifier for Sicmutils TeX operator"] [cm :id "e1" :fid :f1 :src "\n(defn mjtex [v f & {:keys [simp?], :or {simp? true}}]\n  (let [simpfn (if simp? env/simplify identity)]\n    (-> v f simpfn\n      ->TeX ((partial format \"\\\\(%s\\\\)\")))))\n\n;; Try it out\n(mjtex 'x cube)\n(mjtex 'x (square sin))\n(mjtex 'y (env/+ sin cos))\n\n;; Note, the default simplify makes this just 1\n(mjtex 'x (env/+ (square sin) (square cos)))\n(mjtex 'x (env/+ (square sin) (square cos)) :simp? false)\n" :readonly false :height "280px" :out-height "150px" :layout :left-right]]]], :pos :after, :at :end, :fid :f1, :left [[p {:style {:min-width "50px", :width "50px"}}]]}, :session-name "Exploring", :opts {:mode "vega-lite", :source false, :export {:png true, :svg true}, :renderer "canvas", :scaleFactor 1, :editor true}, :msgop :tabs, :tab {:label "Chap5", :id :chap5, :opts {:eltsper 2, :size "auto", :rgap "20px", :cgap "20px", :order :row}}}} {:usermeta {:frame {:top [[gap :size "50px"] [v-box :gap "5px" :children [[md "* Compute data on server: f, f', f(values) f'(values)"] [cm :id "e2" :fid :f2 :src "\n(defn get-data [f values]\n  (let [df (D f)\n        fx (mjtex 'x f)\n        dfx (mjtex 'x df)\n        fvalues (mapv f values)\n        dfvalues (mapv df values)]\n    [fx dfx values fvalues dfvalues]))\n\n;; Try it out\n(get-data cube (range 5))\n(defn afn [x] (env/+ (square x) (cube x)))\n(get-data afn (range 10))\n" :readonly false :height "280px" :out-height "150px" :layout :left-right]]]], :pos :after, :at :end, :fid :f2, :left [[p {:style {:min-width "50px", :width "50px"}}]]}, :session-name "Exploring", :opts {:mode "vega-lite", :source false, :export {:png true, :svg true}, :renderer "canvas", :scaleFactor 1, :editor true}, :msgop :tabs, :tab {:label "Chap5", :id :chap5, :opts {:eltsper 2, :size "auto", :rgap "20px", :cgap "20px", :order :row}}}} {:encoding {:y {:field "y", :type "quantitative"}, :color {:field "ftype", :type "nominal"}, :x {:field "x", :type "quantitative"}, :tooltip [{:field "x", :type "quantitative"} {:field "y", :type "quantitative"}]}, :usermeta {:frame {:top [[gap :size "50px"] [md "#### Plot f, f'"]], :pos :after, :at :end, :right [[gap :size "20px"] [md {:style {:font-size "16px", :width "800px"}} "f = \\({x}^{3} + {x}^{2}\\)\n f' = \\(3\\,{x}^{2} + 2\\,x\\)"]], :fid :f3, :left [[gap :size "50px"] [h-box :gap "5px" :children [[cm :id "e3" :fid :f3 :readonly false :vid :fdf1 :height "400px" :out-height "150px" :out-width "600px" :src "\n(let [[ftex dftex values fvalues dfvalues]\n      (clj (get-data probfn (range 10)))]\n  (aerial.hanami.core/visualize\n   (let [fvpairs (->> fvalues (interleave (repeat \"f\"))\n                      (partition-all 2))\n         dfvpairs (->> dfvalues (interleave (repeat \"f'\"))\n                       (partition-all 2))]\n     (hc/xform\n      ht/point-chart\n      :FID :f3 :VID :fdf1\n      :TXT (str \"f = \" ftex \"\\n f' = \" dftex)\n      :DATA (mapv (fn [x [ftype y]] {:x x, :y y, :ftype ftype})\n                  (concat values values)\n                  (concat fvpairs dfvpairs))\n      :HEIGHT 500 :WIDTH 600 :MSIZE 80 :COLOR \"ftype\"\n      :LEFT '[[p {:style {:width \"50px\", :min-width \"50px\"}}]]\n      :RIGHT '[[gap :size \"20px\"]\n               [md {:style {:font-size \"16px\", :width \"800px\"}}\n                   :TXT]]))\n   (js/document.getElementById \"fdf1\")))"] [md {:style {:min-width "80px", :width "80px"}} ""]]]]}, :vid :fdf1, :session-name "Exploring", :opts {:mode "vega-lite", :source false, :export {:png true, :svg true}, :renderer "canvas", :scaleFactor 1, :editor true}, :msgop :tabs, :tab {:label "Chap5", :id :chap5, :opts {:eltsper 2, :size "auto", :rgap "20px", :cgap "20px", :order :row}}}, :mark {:type "circle", :size 80}, :width 600, :background "floralwhite", :height 500, :data {:values [{:y 0, :ftype "f", :x 0} {:y 2, :ftype "f", :x 1} {:y 12, :ftype "f", :x 2} {:y 36, :ftype "f", :x 3} {:y 80, :ftype "f", :x 4} {:y 150, :ftype "f", :x 5} {:y 252, :ftype "f", :x 6} {:y 392, :ftype "f", :x 7} {:y 576, :ftype "f", :x 8} {:y 810, :ftype "f", :x 9} {:y 0, :ftype "f'", :x 0} {:y 5, :ftype "f'", :x 1} {:y 16, :ftype "f'", :x 2} {:y 33, :ftype "f'", :x 3} {:y 56, :ftype "f'", :x 4} {:y 85, :ftype "f'", :x 5} {:y 120, :ftype "f'", :x 6} {:y 161, :ftype "f'", :x 7} {:y 208, :ftype "f'", :x 8} {:y 261, :ftype "f'", :x 9}]}}]}}]

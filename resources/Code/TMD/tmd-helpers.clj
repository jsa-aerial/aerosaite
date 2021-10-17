
:clj

(defn desc-stats [ds]
  (let [ds (tc/info ds)]
    (-> ds
      (tc/add-or-replace-columns
       (let [sd (ds :standard-deviation)
             vr (dtype/make-reader 
                 :float64 (tc/row-count ds)
                 (let [x (sd idx)]
                   (if (number? x) (* x x) -1)))]
         {:variance vr}))
      (tc/rename-columns {:standard-deviation :std-dev})
      (tc/select-columns
       [:col-name :min :max :mean :std-dev :variance :skew])
      (tc/reorder-columns [:col-name :min :max] [:mean :std-dev :variance]))))


(defn colfreq [ds col cntcol]
  (-> ds
    (tc/group-by [col])
    (tc/aggregate {cntcol tc/row-count})))



(defn reduce-cols [keycol col-reducer-map ds-seq]
  (let [colnames (-> ds-seq first tc/column-names)]
    (dsr/group-by-column-agg
     keycol
     (-> colnames
       (->> (mapv #(vector % (dsr/first-value %)))
            (into (array-map)))
       (merge col-reducer-map))
     ds-seq)))





:end
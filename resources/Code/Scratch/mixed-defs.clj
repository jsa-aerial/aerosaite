

(def state-data [1 2 3])

:a
(def state-data (clj state-data))

:b
(def foo
  (let [[cols data] (clj (let [cols [:a :b :c]]
                           [cols [(range 3) (range 10 13)]]))]
    (map (fn[v] (zipmap cols v)) data)))

(let [[cols data] (clj (let [cols [:a :b :c]]
                         [cols [(range 3) (range 10 13)]]))]
  (def foo (map (fn[v] (zipmap cols v)) data)))


(def bar foo)

:a
(def foo (clj (let [cols [:a :b :c]]
                [cols (range 3)])))

(defn cljfn [x] (clj (* x x)))
(def cljfn-memoized
  (memoize cljfn))

(def foo (let [x (cljfn 7)] (inc x)))
(def bar (let [x (cljfn-memoized 9)] x))

(/ 2.5 1.8)
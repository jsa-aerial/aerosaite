


;;; Base deps and requires for TMD and TC
(deps '[[techascent/tech.ml.dataset "6.065"]
        [scicloj/tablecloth "6.051"]])

(require '[tech.v3.dataset :as ds]
         '[tech.v3.datatype :as dtype]
         '[tech.v3.datatype.functional :as df]
         '[tech.v3.dataset.reductions :as dsr]
         '[tablecloth.api :as tc])

;;; If you are using SQL, first need connector and next.jdbc
;;; next.jdbc supports data-source connection that is a java
;;; connector class instance suitable for TMD.SQL
(deps '[[mysql/mysql-connector-java  "5.1.41"]
        [seancorfield/next.jdbc "1.0.424"]])

(require '[next.jdbc :as jdbc]
         '[next.jdbc.result-set :as rs])

;;; Actual tmd.sql deps / requires
(deps '[[techascent/tech.ml.dataset.sql "6.046-01"]])

(require '[tech.v3.dataset.sql :as dsql])

;;; Example data-source and connection usable by tmd.sql:
(def refseq77-db
  {:dbtype "mysql" :user "root" :password "the-pw" :dbname "refseq77"})
(def r77con (-> refseq77-db jdbc/get-datasource jdbc/get-connection))

;;; Now r77con is usable by tmd.sql:
(dsql/sql->dataset r77con "select count(*) from bioentry")









(require '[tech.v3.dataset.rolling :as drl]
         '[tech.v3.dataset.column :as dsc])

(-> [[:a [1 2 3]] [:b [6 7 8]]]
    tc/dataset
    (drl/rolling {:window-type :fixed
                  :window-size 2
                  :relative-window-position :right
                  :edge-mode :zero}
                 {:suma (drl/sum :a) :sumb (drl/sum :b)})
    (tc/add-column
     :sum (fn[ds]
            (dsc/column-map
             (fn[a b] (+ a b))
             :long
             (ds :suma) (ds :sumb)))))
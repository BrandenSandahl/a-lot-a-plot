(ns a-lot-a-plot.utils.utils
  (:require [clojure.java.jdbc :as sql]))
 

(def spec (or (System/getenv "DATABASE_URL")
              "postgresql://localhost:5432/a_lot_a_plot"))

(defn create [plant]
  (sql/insert! spec :plant [:name :description :type] [(:name plant) (:description plant) (:type plant)]))


;(defn all []
;  (let [all (into {} (sql/query spec ["select * from plant order by id desc"]))]
;    (println all))
;  all)
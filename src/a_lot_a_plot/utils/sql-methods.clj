(ns a-lot-a-plot.utils.sql-methods)
 

(def spec (or (System/getenv "DATABASE_URL")
              "postgresql://localhost:5432/a_lot_a_plot"))
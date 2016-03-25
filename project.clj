(defproject a-lot-a-plot "0.0.1-SNAPSHOT"
  :description "Alot Aplot website"
   :dependencies [[org.clojure/clojure "1.8.0"]
                 [ring "1.4.0"]
                 [compojure "1.5.0"]
                 [hiccup "1.0.5"]
                 [org.clojure/java.jdbc "0.4.1"]
                 [org.postgresql/postgresql "9.4-1201-jdbc41"]]
  :javac-options ["-target" "1.6" "-source" "1.6" "-Xlint:-options"]
  :aot [a-lot-a-plot.core]
  :main a-lot-a-plot.core)

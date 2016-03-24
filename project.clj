(defproject a-lot-a-plot "0.0.1-SNAPSHOT"
  :description "FIXME: write description"
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :javac-options ["-target" "1.6" "-source" "1.6" "-Xlint:-options"]
  :aot [a-lot-a-plot.core]
  :main a-lot-a-plot.core)

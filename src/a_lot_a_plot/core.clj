(ns a-lot-a-plot.core
  (:require [clojure.string :as str] ;this brings in a new lib
            [clojure.walk :as walk] ;brings in walk lib 
            [compojure.core :as core]
            [compojure.route :as route]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.params :as params]
            [ring.util.response :as response]
            [hiccup.core :as h]
            [clojure.java.jdbc :as sql]
            [hiccup.form :as form]
            [a-lot-a-plot.views.layout :as layout]
            [a-lot-a-plot.views.contents :as contents]
            [a-lot-a-plot.utils.utils :as utils]
   (:gen-class)))

(defonce server (atom nil))

(def spec (or (System/getenv "DATABASE_URL")
              "postgresql://localhost:5432/a_lot_a_plot"))


(defn migrated? []
  (-> (sql/query spec
                 [(str "select count(*) from information_schema.tables "
                       "where table_name='plant'")])
      first :count pos?))


(defn migrate []
  (when (not (migrated?))
   (sql/db-do-commands spec
                        (sql/create-table-ddl
                         :plant
                         [:id :serial "PRIMARY KEY"]
                         [:name :varchar "NOT NULL"]
                         [:description :varchar "NOT NULL"]
                         [:type :varchar "NOT NULL"]))))
   
(defn create
  [plant]
  (utils/create plant)
  (response/redirect "/"))



(core/defroutes routes
  (core/GET "/" [] (layout/application "Home" (contents/index) (contents/list)))
  (core/POST "/add-plant" request (create (walk/keywordize-keys (:params request))))
  (route/resources "/"))
    

(defn -main []
  (migrate)
 (when @server
    (.stop @server))
 (reset! server (jetty/run-jetty (params/wrap-params routes) {:port 3000 :join? false})))



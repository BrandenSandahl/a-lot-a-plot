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
    

(defn all []
  (into [] (sql/query spec ["select * from plant order by id desc"])))

(core/defroutes routes
  (core/GET "/" [] (layout/application "Home" (contents/index)))
  (route/resources "/"))

;  (core/GET "/" request
;    (let [params (:params request)])
;    (h/html
;       [:html
;        [:head
;         [:link {:rel "stylesheet" :href "http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"}]]
;        [:body
;         [:div.container
;          [:form {:action "/add-plant" :method "post" :role "form" :class "row"}
;           [:fieldset
;              [:legend "Enter a plant to allot to your plot"]
;            [:div.row 
;             [:div.form-group.col-md-5 
;              [:label {:for "name"} "name"]
;              [:input.form-control {:id "name" :placeholder "name"}]]]
;            [:div.row
;             [:div.form-group.col-md-5
;              [:label {:for "type"} "type"]
;              [:input.form-control {:id "type" :placeholder "type"}]]]
;            [:div.row
;             [:div.form-group.col-md-5
;              [:label {:for "description"} "description"]
;              [:textarea.form-control {:placeholder "description" :rows "4" :cols "30"}]]]
;            [:div.row
;             (form/submit-button "submit")]]]]]])))
    

(defn -main []
  (migrate)
 (when @server
    (.stop @server))
 (reset! server (jetty/run-jetty (params/wrap-params routes) {:port 3000 :join? false})))



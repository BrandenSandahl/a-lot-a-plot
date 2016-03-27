(ns a-lot-a-plot.views.contents
  (:use [hiccup.form]
        [hiccup.element :only (link-to)]
        [clojure.java.jdbc]
        [a-lot-a-plot.utils.utils]))
        
      
(defn list []
    (let [all (into [] (query spec ["select * from plant order by id desc"]))]
       [:div.row  
        [:ol
          (map (fn [plant]
                 (println plant)  
                 [:li (str "Name: "(:name plant) " type: " (:type plant))])
              all)]]))

(defn index []
    [:form {:action "/add-plant" :method "post" :class "row"}
     [:fieldset
       [:legend "Enter a plant to allot to your plot"]
      [:div.row 
        [:div.form-group.col-md-5 
          [:label {:for "name"} "name"]
          [:input.form-control {:name "name" :placeholder "name"}]]]
      [:div.row
        [:div.form-group.col-md-5
          [:label {:for "type"} "type"]
          [:input.form-control {:name "type" :placeholder "type"}]]]
      [:div.row
        [:div.form-group.col-md-5
           [:label {:for "description"} "description"]
           [:textarea.form-control {:placeholder "description" :rows "4" :cols "30" :name "description"}]]]
      [:div.row
        [:button {:type "submit"}"submit"]]]])

  
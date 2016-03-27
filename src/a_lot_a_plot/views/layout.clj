(ns a-lot-a-plot.views.layout
  (:use [hiccup.page :only (html5 include-css include-js)]
        [a-lot-a-plot.views.contents :as contents]))

(defn application [title & content]
  (html5 {:ng-app "my-app" :lang "en"}
         [:head
          [:title title]
          (include-css "http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css")
          (include-js "http://code.angularjs.org/1.2.3/angular.min.js")
          (include-js "js/ui-bootstrap-tpls-0.7.0.min.js")
          (include-js "js/script.js")]
         [:body
           [:div {:class "container"} content]]))


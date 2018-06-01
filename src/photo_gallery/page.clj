(ns photo-gallery.page
  (:require [hiccup.core :as html]
            [hiccup.page :as html-page]))

(def images (clojure.java.io/file "resources/img"))

(defn img-display [link]
  [:img {:src link :width "25%" :height "25%" :id (name link) :class "thumbnail"}])

(defn group-display [name]
  [:h1 (last (re-find  #"(resources/img/)([A-Za-z\-]+)" name))])

(def images (mapv (fn [img]
                    (if (.isFile img) (img-display (.getPath img)) (group-display (.getPath img)))) (file-seq images)))
(def button [:button {:id "modal-button"} "Open Modal"])
(def modal [:div {:id "image-modal" :class "modal"}
            [:div {:class "modal-content"}
             [:span {:class "close" :id "close"} "&times;"]
             [:p {:id "content"}]]])

(defn -main []
    (spit "index.html"
          (html-page/html5
           (html/html [:head [:meta {:charset "UTF-8"}] (html-page/include-css "resources/main.css")])
           (html/html [:body button modal (into [:div] images)
                       (html-page/include-js "resources/js/main.js")]))))

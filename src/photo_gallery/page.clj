(ns photo-gallery.page
  (:require [hiccup.core :as html]
            [hiccup.page :as html-page]))

(defn valid-extension? [path]
    (let [valid-exts [".gif" ".png" ".jpg"]
          filename (.getName path)]
      (some true? (-> filename
                      (clojure.string/lower-case)
                      (as-> lowercase-name (map #(clojure.string/includes? lowercase-name %) valid-exts))))))

(def image-files (clojure.java.io/file "resources/img"))

(defn create-img-tag [img]
  (if (valid-extension? img)
    [:img {:src (.getPath img) :width "25%" :height "25%" :id (name (.getPath img)) :class "thumbnail"}]))

(defn get-trip-name [name]
  [:h1 (last (re-find  #"(resources/img/)([A-Za-z\-]+)" (.getPath name)))])

;; (.isFile (first (file-seq image-files))) => false
;; (.isFile (nth (file-seq image-files) 2)) => true
(def images (mapv (fn [file-object]
                    (if (.isFile file-object)
                      (create-img-tag file-object)
                      (get-trip-name file-object))) (file-seq image-files)))

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

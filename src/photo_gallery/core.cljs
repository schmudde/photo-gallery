(ns photo-gallery.core
  (:require [goog.dom :as dom]
            [goog.events :as events]))

(def modal (dom/getElement "image-modal"))
(def modal-button (dom/getElement "modal-button"))
(def close-button (dom/getElement "close"))
(def content (dom/getElement "content"))

(set! (.-onclick modal-button) (fn [] (.setAttribute modal "style" "display: block")))
(set! (.-onclick close-button) (fn [] (.setAttribute modal "style" "display: none") (dom/removeChildren content)))

(defn add-listeners [image]
  (let [id (.getAttribute image "id")]
    (events/listen image "click"
      (fn [event]
          (dom/appendChild content (dom/createDom "img" #js {:src (str id) :width "100%" :height "100%"}))
          (.setAttribute modal "style" "display: block")))))

(doall (map #(add-listeners %) (array-seq (dom/getElementsByClass "thumbnail"))))

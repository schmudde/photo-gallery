(ns photo-gallery.modal
  (:require [goog.dom :as dom]
            [goog.events :as events]))

(def modal (dom/getElement "image-modal"))
(def content (dom/getElement "content"))
(def close-button (dom/getElement "close"))

;; side-effect: attach a listener to the modal close button
(set! (.-onclick close-button) (fn [] (.setAttribute modal "style" "display: none") (dom/removeChildren content)))

(defn add-listeners [image]
  (let [id (.getAttribute image "id")]
    (events/listen image "click"
      (fn [event]
          (dom/appendChild content (dom/createDom "img" #js {:src (str id) :width "100%" :height "100%"}))
        (.setAttribute modal "style" "display: block")))))

;; side-effect: add listeners to all images on the page
(doseq [image-elements (array-seq (dom/getElementsByClass "thumbnail"))] (add-listeners image-elements))

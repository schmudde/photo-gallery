# Photo Gallery

This is a simple photo gallery.

## Walkthrough

### Clojure

```
(require '[hiccup.core :as html])
(require '[hiccup.page :as html-page])

(def img (clojure.java.io/file "resources/img"))
(file-seq img)

(defn img-display [link] [:img {:src link :width "25%" :height "25%" :id (name link) :class "thumbnail"}])
(defn group-display [name] [:h1 (last (re-find  #"(resources/img/)([A-Za-z\-]+)" name))])
(def images (mapv #(if (.isFile %) (img-display (.getPath %)) (group-display (.getPath %))) (file-seq img)))
(def button [:button {:id "modal-button"} "Open Modal"])
(def modal [:div {:id "image-modal" :class "modal"} [:div {:class "modal-content"} [:span {:class "close" :id "close"} "&times;"] [:p {:id "content"}]]])

(spit "index.html" (html-page/html5 (html/html [:head [:meta {:charset "UTF-8"}] (html-page/include-css "/resources/main.css")]) (html/html [:body (html-page/include-js "/resources/js/main.js") button modal (into [:div] images)])))
```
### ClojureScript

#### Launching the REPL

`clj --main cljs.main -d resources/js --compile photo-gallery.core --repl`

#### Run some CLJS

```
(require '[goog.dom :as dom])

(def modal (dom/getElement "image-modal"))
(def modal-button (dom/getElement "modal-button"))
(def close-button (dom/getElement "close"))
(def content (dom/getElement "content"))
```

#### Add Events

```
(set! (.-onclick modal-button) (fn [] (.setAttribute modal "style" "display: block")))
(set! (.-onclick close-button) (fn [] (.setAttribute modal "style" "display: none") (dom/removeChildren content)))

(map #(.getAttribute % "id") (array-seq (dom/getElementsByClass "thumbnail")))
(require '[goog.events :as events])

(defn add-listeners [image]
  (let [id (.getAttribute image "id")]
    (events/listen image "click"
      (fn [event]
          (dom/appendChild content (dom/createDom "img" #js {:src (str id) :width "100%" :height "100%"}))
          (.setAttribute modal "style" "display: block")))))

(map #(add-listeners %) (array-seq (dom/getElementsByClass "thumbnail")))
```

(ns lemonpi-reagent.core
  (:require
   [reagent.core :as reagent :refer [atom]]
   [reagent.dom :as rdom]
   [ajax.core :refer [GET]]
   [lemonpi-reagent.table :refer [table]]))


(def advertisers-url "https://5b87a97d35589600143c1424.mockapi.io/api/v1/advertisers")

(def stats-url "https://5b87a97d35589600143c1424.mockapi.io/api/v1/advertiser-statistics")

(def columns [{:id 1 :display "Advertiser" :classes nil}
              {:id 2 :display "Creation Date" :classes nil}
              {:id 3 :display "# Campaigns" :classes "number"}
              {:id 4 :display "Impressions" :classes "number"}
              {:id 5 :display "Clicks" :classes "number"}])

(defonce advertisers (atom {:loading true :data [] :error false :merged false}))
(defonce stats (atom {:loading true :data [] :error false}))

;; ----------------------------------
;; Landing Page

(defn should-merge? [stats advertisers]
  (and
   (seq (:data @stats))
   (seq (:data @advertisers))
   (not (:merged @advertisers))))

(defn merge-stats-data [stats advertisers]
  (.log js/console "merge-stats-data" (:merged @advertisers) (should-merge? stats advertisers))
  (if (should-merge? stats advertisers)
    ;; turn stat data into a map, so lookups when mapping over advertisers will be quick
    (let [stat-map (zipmap (map #(% "advertiserId") (:data @stats)) (:data @stats))]
      (swap! advertisers (fn [cur]
                           {:loading false
                            :error false
                            :merged true
                            :data (map (fn [row]
                                         (merge row (or (stat-map (row "id")) {})))
                                       (:data cur))})))))

(defn error-handler [_]
  (swap! advertisers assoc :loading false :error true))

(defn api-handler [state response]
  (swap! state assoc :loading false :data response)
  ;; I don't like this way of handling things, but for now it will ensure that the 
  ;; data is filled properly.  I probably should be using core.async?
  (merge-stats-data stats advertisers))

(defn home-page []
  (let [_advertiser-data (GET advertisers-url
                           :handler (partial api-handler advertisers)
                           :error-handler error-handler
                           :response-format :json)
        _stats-data (GET stats-url
                      :handler (partial api-handler stats)
                      :response-format :json)]
    (fn []
      [table "Overview of Advertisers"  advertisers columns])))

;; -------------------------
;; Initialize app

(defn init! []
  (rdom/render [home-page] (.getElementById js/document "app")))

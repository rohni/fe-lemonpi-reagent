(ns lemonpi-reagent.table
  (:require
   [lemonpi-reagent.utils :refer [format-date]]))

(defn table-header [columns]
  [:thead
   [:tr
    (map (fn [{:keys [id display classes]}]
           ^{:key (str "column-" id)}
           [:th {:class classes} display]) columns)]])

(defn data-row [row]
  ^{:key (str "advertiser-" (row "id"))} ;; <-- not sure why this is not working
  [:tr
   [:td (row "name")]
   [:td.date (format-date (row "createdAt"))]
   [:td.number (count (row "campaignIds"))]
   [:td.number (or (row "impressions") "n/a")]
   [:td.number (or (row "clicks") "n/a")]])

(defn message-row [num-cols message]
  [:tr
   [:td {:colSpan num-cols} message]])

(defn table [title state columns]
  [:div.table
   [:h3 title]
   [:table
    [table-header columns]
    [:tbody
     (cond
       (:loading @state) [message-row (count columns) "Loading..."]
       (:error @state) [message-row (count columns) "Could not load data :-("]
       :else
       (map (fn [row] [data-row row]) (:data @state)))]]])

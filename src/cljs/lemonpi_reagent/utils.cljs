(ns lemonpi-reagent.utils
  (:require
   [goog.string :as gstring]
   [goog.string.format]))

;; Looked at some libraries, but I only need this one formatter
;; for dates, so just stayed with the js version (could be better)
(defn format-date [dstring]
  (let [pad (fn [s] (gstring/format "%02d" s))
        jsDate (js/Date. dstring)
        year (.getFullYear jsDate)
        month (pad (inc (.getMonth jsDate)))
        day (pad (.getDate jsDate))
        hour (pad (.getHours jsDate))
        minute (pad (.getMinutes jsDate))]
    (str day "-" month "-" year " " hour ":" minute)))

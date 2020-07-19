(ns lemonpi-reagent.prod
  (:require [lemonpi-reagent.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)

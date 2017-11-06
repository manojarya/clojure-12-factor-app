(ns clojure-12-factor-app.core
  (:require
            [clojure-12-factor-app.database :as db]
            [com.stuartsierra.component :as component]
            [clojure-12-factor-app.helper :as helper]
            [clojure.tools.namespace.repl :refer [refresh]]
            [clojure.tools.logging :as log])
  (:gen-class))

(defn start [active-profile env-map]
  (log/info "starting 12 factor clojure app in mode " active-profile)
  (let [config-map  (helper/load-configuration "resources/config.edn")
        property-files (get-in config-map [:profile active-profile :resource-paths])
        resolved-property-files (reduce (fn [v file] (conj v (helper/resolve-env-parameter file env-map))) [] property-files)]
    (into {} (helper/load-properties resolved-property-files))))


(defn example-system [config-options]
  (component/system-map
    :db (db/new-database (:service.database.host config-options)
                         (:service.database.port config-options)
                         (:service.database.name config-options)
                         (:service.database.username config-options)
                         (:service.database.password config-options))))

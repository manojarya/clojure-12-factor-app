(ns clojure-12-factor-app.core
  (:require
            [clojure-12-factor-app.database :as db]
            [com.stuartsierra.component :as component]
            [clojure-12-factor-app.config :as helper]
            [clojure.tools.namespace.repl :refer [refresh]]
            [clojure.tools.logging :as log])
  (:gen-class))

(defrecord App [options cache database]
  component/Lifecycle
  ;starts the database component
  (start [this]
      (log/info "starting my application .....")
      (-> this
          (assoc :options options)
          (assoc :cache cache)
          (assoc :db database)))

  (stop [this]
    (log/info "stopping my application.....")
    this))

(defn new-app [opts]
  (map->App         {:options opts
                     :cache (atom {})}))

(defn get-app-data [database query]
  (db/select (:db-connection database) query))
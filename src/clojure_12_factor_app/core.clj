(ns clojure-12-factor-app.core
  (:require
            [clojure-12-factor-app.database-component :as db]
            [com.stuartsierra.component :as component]
            [mount.core :as mount]
            [clojure-12-factor-app.config :as config]
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


(defn start-app [& {:keys [active-profile environment]
                :or { active-profile :default
                     environment    {}}}]
  (config/start active-profile environment)
  (mount/start))


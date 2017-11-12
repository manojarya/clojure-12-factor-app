(ns clojure-12-factor-app.database-component
  (:require  [com.stuartsierra.component :as component]
             [clojure.tools.logging :as log]))

(defn connect-to-database [host port name user password]
  (log/info "connecting database host:"  host  "port:" port "db-name:" name "user:" user)
  {:host host :port port :name name :user user :password password})

(defn disconnect-database [conn]
  (log/info "disconnecting database host " conn))

(defn select [connection query]
  (log/info "executing query" query "using connection" connection)
  query)

(defrecord Database [host port name user password]
  component/Lifecycle
  ;starts the database component
  (start [component]
      (log/info "starting database host" host "port:" port "name" name "user" user)
      (let [conn (connect-to-database host port name user password)]
        (assoc component :db-connection conn)))

  ;stops the database component
  (stop [component]
    (log/info "stopping database host" host "port:" port "name" name "user" user)
    (disconnect-database (:db-connection component))
    (assoc component :db-connection nil)))

(defn new-database [host port name user password] "ctor for the database record"
  (map->Database {:host host :port port :name name :user user :password password}))
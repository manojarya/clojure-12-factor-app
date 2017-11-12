(ns clojure-12-factor-app.database
  (:require
    [mount.core :refer [defstate]]
    [clojure-12-factor-app.config :refer [config-options]]
    [clojure.tools.logging :as log]))

(defn- connect-to-database [config-options]
  (log/info "connecting to mount-database with config-options" config-options)
  {:host (:service.database.host config-options)
   :port (:service.database.port config-options)
   :name (:service.database.name config-options)
   :user (:service.database.username config-options)
   :password (:service.database.password config-options)})

(defstate conn :start (connect-to-database config-options))

(defn select [query]
  (log/info "executing query" query "using connection" conn)
  query)

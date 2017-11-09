(ns clojure-12-factor-app.core-test
  (:require [clojure.test :refer :all]
            [clojure-12-factor-app.core :refer :all]
            [clojure-12-factor-app.database :as db]
            [clojure-12-factor-app.helper :as helper]
            [com.stuartsierra.component :as component]))

(defn example-system [config-options]
  (component/system-map
    ;;db component
    :db (db/new-database (:service.database.host config-options)
                         (:service.database.port config-options)
                         (:service.database.name config-options)
                         (:service.database.username config-options)
                         (:service.database.password config-options))
    ;;ur app
    :app (component/using
           (new-app config-options)
           {:database :db})))

(deftest valid-config-start-components-init-system
  (testing "example system start/stop"
    (let [example-sys-1  (example-system  (get-config :default {}))
          app-system-map (component/start example-sys-1)]
      (testing "system-map has app and db initialized"
        (is (helper/not-nil? (get-in app-system-map [:db :db-connection])) "db connection can not be null.")))))


(deftest valid-config-start-components-usage
  (testing "example system start/stop"
    (let [example-sys-1  (example-system  (get-config :default {}))
          app-system-map (component/start example-sys-1)]
      (testing "select query"
        (is (helper/not-nil? (get-app-data (:db app-system-map) "query1")) "db connection can not be null.")))))
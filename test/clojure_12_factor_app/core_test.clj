(ns clojure-12-factor-app.core-test
  (:require [clojure.test :refer :all]
            [clojure-12-factor-app.core :refer :all]
            [clojure-12-factor-app.database-component :as db]
            [clojure-12-factor-app.database :as database]
            [clojure-12-factor-app.config :as config]
            [com.stuartsierra.component :as component]
            [mount.core :as mount]
            ))

(def not-nil? (complement nil?))

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

(deftest valid-config-start-components-usage
  (let [example-sys-1  (example-system (config/load-config :default {}))
        app-system-map (component/start example-sys-1)]
    (testing "select query"
      (is (= "query1" (get-app-data (:db app-system-map) "query1"))))))


(deftest valid-config-start-mount
  (start-app )
  (mount/start)
  (testing "select query"
    (is (= "query1" (database/select "query1")))))

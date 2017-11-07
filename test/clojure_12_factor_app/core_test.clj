(ns clojure-12-factor-app.core-test
  (:require [clojure.test :refer :all]
            [clojure-12-factor-app.core :refer :all]
            [clojure-12-factor-app.database :as db]
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
           {:database :db})
    ))

(deftest a-test
  (testing "example system start/stop"
    (let [sys1  (example-system  (get-config :default {}))]
      (component/start sys1))))

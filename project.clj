(defproject clojure-12-factor-app "0.1.0-SNAPSHOT"

  :description "FIXME: write description"

  :url "http://example.com/FIXME"

  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [
                 ;clojure core and tooling
                 [org.clojure/clojure "1.8.0"]
                 [org.clojure/tools.namespace "0.2.11"]

                 ;dependency and lifecycle management
                 [com.stuartsierra/component "0.3.2"]

                 [propertea "1.2.3"]

                 ;logging
                 [org.clojure/tools.logging "0.3.1"]
                 [ch.qos.logback/logback-classic "1.2.3"]
                 ]

  :main ^:skip-aot clojure-12-factor-app.core

  :target-path "target/%s"

  :profiles {:uberjar {:aot :all}})

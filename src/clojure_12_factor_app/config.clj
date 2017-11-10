(ns clojure-12-factor-app.config
  (:require
            [clojure.tools.logging :as log]
            [clojure.edn :as edn]
            [clojure.java.io :as io]
            [propertea.core]))

(def ^:const config-file "resources/config.edn")

(def not-nil? (complement nil?))

(defn- resolve-environment-variable [exp lookup-map] "resolves pattern ${env-var} in the exp using lookup-map"
  (let [place-holder-regex (re-pattern "\\$\\{([^}]+)\\}")
        matcher (re-matcher place-holder-regex exp)
        matches (take-while  not-nil?  (repeatedly  #(re-find  matcher)))]
    (reduce (fn [exp [k v]] (clojure.string/replace exp k (get lookup-map v))) exp matches)))

(defn- resolve-environment-variables [property-files environment]
  (reduce (fn [v file] (conj v (resolve-environment-variable file environment))) [] property-files))

(defn- load-properties [property-files] "loads the java style property files"
  (into {} (reduce (fn [m file] (merge m (propertea.core/read-properties file)))
                   [] property-files)))

(defn load [& {:keys [active-profile environment]
                     :or { active-profile :default
                           environment    {}}}]
  (log/info "loading config:" config-file  "profile:" active-profile "environment:")
  (->
    (edn/read-string (slurp (io/reader config-file)))
    (get-in [:profile active-profile :resource-paths])
    (resolve-environment-variables environment)
    (load-properties)))


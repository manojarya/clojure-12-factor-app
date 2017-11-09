(ns clojure-12-factor-app.config
  (:require
            [clojure.tools.logging :as log]
            [clojure.edn :as edn]
            [clojure.java.io :as io]
            [propertea.core]))

(def not-nil? (complement nil?))

(defn resolve-env-parameter [exp lookup-map] "resolves pattern in the exp using lookup-map"
  (let [place-holder-regex (re-pattern "\\$\\{([^}]+)\\}")
        matcher (re-matcher place-holder-regex exp)
        matches (take-while  not-nil?  (repeatedly  #(re-find  matcher)))]
    (reduce (fn [exp [k v]] (clojure.string/replace exp k (get lookup-map v))) exp matches)))

(defn resolve-property-files [property-files env-map]
  (reduce (fn [v file] (conj v (resolve-env-parameter file env-map))) [] property-files))

(defn load-properties [property-files]
  (into {} (reduce (fn [m file] (merge m (propertea.core/read-properties file)))
                   [] property-files)))

(defn load-configuration [active-profile env-map]
  (log/info "reading configuration: resources/config.edn" )
  (->
    (edn/read-string (slurp (io/reader "resources/config.edn")))
    (get-in [:profile active-profile :resource-paths])
    (resolve-property-files  env-map)
    (load-properties)))


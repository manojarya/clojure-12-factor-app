(ns clojure-12-factor-app.helper
  (:require
            [clojure.tools.logging :as log]
            [clojure.edn :as edn]
            [clojure.java.io :as io]
            [propertea.core]))

(def not-nil? (complement nil?))

(defn load-configuration [config-file]
  (log/info "reading configuration:" config-file)
  (edn/read-string (slurp (io/reader config-file))))


(defn resolve-env-parameter [exp lookup-map] "resolves pattern in the exp using lookup-map"
  (let [place-holder-regex (re-pattern "\\$\\{([^}]+)\\}")
        matcher (re-matcher place-holder-regex exp)
        matches (take-while  not-nil?  (repeatedly  #(re-find  matcher)))]
    (reduce (fn [exp [k v]] (clojure.string/replace exp k (get lookup-map v))) exp matches)))

(defn load-properties [property-files]
  (reduce (fn [m file] (merge m (propertea.core/read-properties file)))
            [] property-files))
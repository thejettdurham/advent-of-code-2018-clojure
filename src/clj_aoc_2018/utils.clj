(ns clj-aoc-2018.utils
  (:require [clojure.java.io :as io] ))

; TODO basic input processing here
(defn read-res-file
  "Reads file from resources as string"
  [file]
  (->> (io/resource file) slurp))
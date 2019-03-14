(ns clj-aoc-2018.day2
  (:require [clj-aoc-2018.utils :as utils]))

(defn count-letters
  "Transforms input id into map of letters to num of occurrences"
  [str]
  (->>
    (group-by identity (clojure.string/split str #""))
    (map (fn [[k v]] [k (count v)]))
    (into {})))

(defn has-exactly-n-repeat-letters
  "Sees that a given counted id has a letter repeated exactly n times, returns 1 if so, 0 if not"
  [n counted]
  (reduce (fn [_ [_ v]]
            (if (= v n)
              (reduced 1)
              0)
            ) 0 counted))

(defn reduce-2-and-3-counts
  "Reduces counted words to num containing exactly 2 and exactly 3 dupe letters"
  [counteds]
  (reduce (fn [[count2 count3] w]
            [(+ count2 (has-exactly-n-repeat-letters 2 w))
             (+ count3 (has-exactly-n-repeat-letters 3 w))]
            ) [0 0] counteds))

(defn edit-distance
  [s1 s2]
  (->> (map vector s1 s2)
       (filter #(apply not= %))
       count))

(defn get-common-characters
  [s1 s2]
  (->> (map vector s1 s2)
       (filter #(apply = %))
       (map first)
       clojure.string/join
       ))

(def input (->> "day2.txt"
                utils/read-res-file
                clojure.string/split-lines
                ))

(defn part1
  []
  (->> input (map count-letters) reduce-2-and-3-counts (apply *)))

(defn part2
  []
  (reduce (fn [matched idO] (if
                              (not (nil? matched))
                              (reduced matched)
                              (reduce (fn [_ idI] (if
                                                    (= (edit-distance idO idI) 1)
                                                    (reduced (get-common-characters idO idI))
                                                    nil
                                                    )) nil input)
                              )) nil input))

(defn -main
  []
  (do (println (str "Part 1: " (part1)))
      (println (str "Part 2: " (part2)))))
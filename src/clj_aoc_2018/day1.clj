(ns clj-aoc-2018.day1
  (:require [clj-aoc-2018.utils :as utils]))

(defn- get-op
  "Reads the operation from an instruction and converts it to the corresponding fn"
  [str]
  (eval (read-string (subs str 0 1))))

(defn- get-val
  "Reads the value from an instruction and converts it to a number"
  [str]
  (read-string (subs str 1)))

(defn get-instruction
  "Creates an executable instruction from an input instruction"
  [instr]
  ((juxt get-op get-val) instr))

(defn calc-reducer
  "Reducer for part 1"
  [acc [op val]]
  (op acc val))

(defn calc-track-reducer
  "Like calc-reducer, but we also accumulate the seen freqs in a set and check if we've seen the next freq "
  [[freq seen-freqs] [op val]]
  (let [next-freq (op freq val)]
    (if
      (contains? seen-freqs next-freq)
      (reduced next-freq)
      [next-freq (conj seen-freqs next-freq)])))

(def input (->> "day1.txt"
                utils/read-res-file
                clojure.string/split-lines
                (map get-instruction)))

(defn part1
  [initial]
  (reduce calc-reducer initial input))

(defn part2
  [initial]
  ; Cycle makes the existing input seq repeat infinitely :fire:
  (reduce calc-track-reducer [initial #{}] (cycle input)))

(defn -main
  "I don't do a whole lot ... yet."
  []
  (do (println (str "Part 1: " (part1 0)))
      (println (str "Part 2: " (part2 0)))))
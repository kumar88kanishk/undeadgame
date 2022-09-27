(ns undeadgame.game-test
  (:require [undeadgame.game :refer :all]
            [expectations :refer :all]))

(defn- find-face-index [game face]
  (first (keep-indexed (fn [index tile]
                         (when (and (= (:face tile) face)
                                    (not (:revealed? tile)))
                           index))
                       (:tiles game))))

(defn reveal-one [face game]
  (reveal-tile game (find-face-index game face)))

;; create-game
(expect {:h1 2 :h2 2 :h3 2 :h4 1 :h5 2 :zo 3 :gy 1 :fg 2}
        (->> (create-game) :tiles (map :face) frequencies))

(expect #(< 10 %) (count (set (repeatedly 100 create-game))))

(expect {:remaining 30} (frequencies (:sand (create-game))))

;; reveal tile

(expect 1 (->> (reveal-tile (create-game) 0)
               :tiles
               (filter :revealed?)
               count))


(expect #{{:face :h1 :revealed? true} {:face :h2 :revealed? true}}
        (->> (create-game)
             (reveal-one :h1)
             (reveal-one :h2)
             :tiles
             (filter :revealed?)
             (set)))

(expect #{{:face :h3 :revealed? true}}
        (->> (create-game)
             (reveal-one :h1)
             (reveal-one :h1)
             (reveal-one :h3)
             :tiles
             (filter :revealed?)
             (set)))
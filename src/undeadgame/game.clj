(ns undeadgame.game)

(def faces [:h1 :h1 :h2 :h2 :h3 :h3 :h4 :h5 :h5
            :zo :zo :zo :gy :fg :fg])

(defn ->tile [face]
  {:face face})

(defn revealed-tiles [game]
  (->> game
       :tiles
       (filter :revealed?)))

(defn can-reveal? [game]
  (> 2 (count (revealed-tiles game))))

(defn- match-revealed [tiles]
  (mapv (fn [tile]
          (if (:revealed? tile)
            (-> tile
                (assoc :matched? true)
                (dissoc :revealed?))
            tile)) tiles))
(defn- get-match [game]
  (let [revealed (revealed-tiles game)]
    (when (and (= 2 (count revealed))
               (= 1 (count (set (map :face revealed)))))
      (:face (first revealed)))))

(defn- check-for-match [game]
  (if-let [match (get-match game)]
    (update-in game [:tiles] match-revealed)
    game))

(defn create-game []
  {:tiles (shuffle (map ->tile faces))
   :sand (repeat 30 :remaining)})

(defn reveal-tile [game index]
  (if (can-reveal? game)
    (-> game
        (assoc-in [:tiles index :revealed?] true)
        (check-for-match))
    game))
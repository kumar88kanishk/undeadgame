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

(defn- replace-remaining [sand replacement]
  (concat
   (take-while (complement #{:remaining}) sand)
   replacement
   (->> (drop-while (complement #{:remaining}) sand)
        (drop (count replacement)))))

(defn- wake-up-dead [tiles]
  (mapv (fn [tile]
          (if (= (:face tile) :gy)
            (assoc tile :face :zo)
            tile))
        tiles))

(defn- perform-match-actions [game match]
  (case match
    :fg (assoc-in game [:foggy?] true)
    :zo (-> game
            (update-in [:sand] #(replace-remaining % (repeat 3 :zo)))
            (update-in [:tiles] wake-up-dead))
    game))

(defn- check-for-match [game]
  (if-let [match (get-match game)]
    (-> game
        (update-in [:tiles] match-revealed)
        (perform-match-actions match))
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


(def sanddy `(:zo :zo :zo :zo :zo :zo :zo :zo :zo :zo))
(def zom  (repeat 2 :zo))
(comment
  (take-while (complement #{:rem}) sanddy)
  (drop-while (complement #{:rem}) sanddy)

  (concat
   (take-while (complement #{:rem}) sanddy)
   zom
   (->> (drop-while (complement #{:rem}) sanddy)
        (drop (count zom))))

  (drop-while (complement #{:remaining}) (repeat 10 :rem)))
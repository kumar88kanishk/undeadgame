(ns undeadgame.web
  (:require [compojure.core :refer [defroutes GET]]
            [compojure.route :refer [resources]]))

(defn index [req]
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    "hello from compojure"})

(defroutes app
  (GET "/" [] index)
  (resources "/"))


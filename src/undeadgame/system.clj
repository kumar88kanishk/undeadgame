(ns undeadgame.system
  (:require
   [com.stuartsierra.component :as component]
   [org.httpkit.server :refer [run-server]]
   [undeadgame.web :refer [app]]))

(defn- start-server [handler port]
  (let [server (run-server handler {:port port})]
    (println "Server started at port " port)
    server))



(defn- stop-server [server]
  (when server
    (server)))

(defrecord WebGame []
  component/Lifecycle
  (start [this]
    (assoc this :server (start-server #'app 9001)))
  (stop [this]
    (stop-server (:server this))
    (dissoc this :server)))

(defn create-system []
  (WebGame.))

(defn -main [& args]
  (.start (create-system)))
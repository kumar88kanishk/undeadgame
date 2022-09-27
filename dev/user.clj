(ns user
  (:require [reloaded.repl :refer [system reset stop]]
            [undeadgame.system]))

(reloaded.repl/set-init! #'undeadgame.system/create-system)
(defproject undeadgame "0.1.0-SNAPSHOT"
  :description "A web based game, learning from parens of the dead"
  :url "http://example.com/FIXME"
  :license {:name "GNU General Public License"
            :url "http://www.gnu.org/licenses/gpl.html"}
  :main undeadgame.system
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/clojurescript "1.11.60"]
                 [com.stuartsierra/component "1.1.0"]
                 [http-kit "2.6.0"]
                 [compojure "1.7.0"]
                 [quiescent "0.3.2"]]
  :profiles {:dev {:dependencies [[reloaded.repl "0.2.4"]
                                  [com.bhauman/figwheel-main "0.2.18"]]
                   :source-paths ["dev"]
                   :cljsbuild {:builds [{:source-paths ["src" "dev"]
                                         :figwheel true
                                         :compiler {:output-to "target/classes/public/app.js"
                                                    :output-dir "target/classes/public/out"
                                                    :optimizations :none
                                                    :recompile-dependents true
                                                    :source-map true}}]}}}
  :aliases {"kaocha" ["run" "-m" "kaocha.runner"]
            "fig" ["trampoline" "run" "-m" "figwheel.main"]})

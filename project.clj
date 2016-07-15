(defproject ws-parrot "0.1.0-SNAPSHOT"
  :main ws-parrot.handler
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [compojure "1.5.1"]
                 [ring/ring-defaults "0.2.1"]
                 [ring "1.5.0"]
                 [clj-http "2.2.0"]]
  :plugins [[lein-ring "0.9.7"]]
  :ring {:handler ws-parrot.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.0"]]}})

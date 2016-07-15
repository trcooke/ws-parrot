(defproject ws-parrot "0.1.0"
  :main ws-parrot.handler
  :description "A testing tool for stubbing out HTTP web services"
  :license "The MIT License (MIT)"
  :url "https://github.com/trcooke/ws-parrot"
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

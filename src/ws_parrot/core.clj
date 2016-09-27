(ns ws-parrot.core
  (:gen-class)
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [cheshire.core :refer :all]
            [ring.adapter.jetty :refer :all]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defn ws [port]
  (let [savedroutes (atom '())
        routes (defroutes app-routes
                  (POST "/parrotthis" [uri method response] (swap! savedroutes conj {:uri uri :method (clojure.string/lower-case method) :response response}))
                  (GET "/parrotthis" [] (generate-string (deref savedroutes)))
                  (ANY "/req" req (println (:request-method req)))
                  (ANY "/*" req (->> (deref savedroutes)
                     (filter #(= (:uri %) (:* (:params req))))
                     (filter #(= (keyword (:method %)) (:request-method req)))
                     (first)
                     (:response)
                     (str)))
                  (route/resources "/")
                  (route/not-found "Not Found"))
        app (wrap-defaults routes (assoc-in site-defaults [:security :anti-forgery] false))]
    (run-jetty app {:port port :join? false})))

(defn start-webserver [port]
  (ws port))

(defn -main [& args]
  (let [port (read-string (first args))]
  (start-webserver port)))

(ns ws-parrot.core
  (:gen-class)
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [cheshire.core :refer :all]
            [ring.adapter.jetty :refer :all]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(def saved-routes (atom '()))

(defroutes app-routes
  (POST "/parrotthis" [uri response] (swap! saved-routes conj {:uri uri :response response}))
  (GET "/parrotthis" [] (generate-string (deref saved-routes)))
  (ANY "/*" req (->> (deref saved-routes)
                     (filter #(= (:uri %) (:* (:params req))))
                     (first)
                     (:response)))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes (assoc-in site-defaults [:security :anti-forgery] false)))

(defn start-webserver [port]
  (run-jetty app {:port port :join? false}))

(defn -main [& args]
  (run-jetty app {:port (read-string (first args))}))

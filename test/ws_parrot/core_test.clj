(ns ws-parrot.core-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [ws-parrot.core :refer :all]
            [clj-http.client :as client]))

(deftest test-app
  (testing "http client stuff"
    (let [server (start-webserver 9001)
          postres (client/post "http://localhost:9001/parrotthis" {:form-params {"uri" "test" "response" "Test content"}})]
      (is (= (:body (client/get "http://localhost:9001/test")) "Test content")))))

(ns ws-parrot.core-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [ws-parrot.core :refer :all]
            [clj-http.client :as client]))

(deftest test-app
  (testing "Save nothing, do a GET request, No response content."
    (let [server (start-webserver 9000)]
      (is (= (:body (client/get "http://localhost:9000/test")) ""))))

  (testing "Save a GET response, do a GET request, response recieved."
    (let [server (start-webserver 9001)
          postres (client/post "http://localhost:9001/parrotthis" {:form-params {"uri" "test" "method" "GET" "response" "Test content 9001"}})]
      (is (= (:body (client/get "http://localhost:9001/test")) "Test content 9001"))))

  (testing "Save a POST response, do a GET request, No response content."
    (let [server (start-webserver 9002)
          postres (client/post "http://localhost:9002/parrotthis" {:form-params {"uri" "test" "method" "POST" "response" "Test content 9002"}})]
      (is (= (:body (client/get "http://localhost:9002/test")) ""))))
  )

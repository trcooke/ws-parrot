# ws-parrot
A testing tool. A standalone web server that you can teach to respond in a particular manner for a given request path.

## Getting from clojars

[![Clojars Project](https://img.shields.io/clojars/v/ws-parrot.svg)](https://clojars.org/ws-parrot)

Leiningen

    [ws-parrot "0.1.0"]

Gradle

    compile "ws-parrot:ws-parrot:0.1.0"

Maven

    <dependency>
        <groupId>ws-parrot</groupId>
        <artifactId>ws-parrot</artifactId>
        <version>0.1.0</version>
    </dependency>

## Starting and stopping the web server
From your code, start the server with

    (start-webserver 9001)

Maybe you want to manually stop the server after it's started? No problem

    (def server (start-webserver 9001))
    ;; Do stuff
    (.stop server)

## Teach it to respond with your data for a given URI

Let's say you want all requests made to `<server>/hello` to return the text `Hello, World!`. Make a `POST` request to `<server>/parrotthis` with params `uri=hello` and `response=Hello, World`. For example:

    curl -d uri=hello -d response="Hello, World" <server>:<port>/parrotthis

Then all subsequent calls to `<server>:<port>/hello` will return the text `"Hello, World"`.

## Example usage with Unit Tests
I wrote this tool for use in Unit Tests to enable you to mock out HTTP endpoints. This example shows how you might use and interract with the server from in your tests. (Uses [clj-http](https://github.com/dakrone/clj-http))

    (ns ws-parrot.handler-test
    (:require [clojure.test :refer :all]
              [ring.mock.request :as mock]
              [ws-parrot.handler :refer :all]
              [clj-http.client :as client]))

    (deftest test-app
      (testing "http client stuff"
        (let [server (start-webserver 9001)
              postres (client/post "http://localhost:9001/parrotthis" {:form-params {"uri" "test" "response" "Test content"}})]
          (is (= (:body (client/get "http://localhost:9001/test")) "Test content")))))

Note that it is not required to explicitly stop the server. The termination of the test function causes it to shutdown.

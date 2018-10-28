(ns restful-clojure.handler
    (:require [compojure.core :refer :all]
              [compojure.route :as route]
              [ring.util.response :as r]
              [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
              [ring.middleware.keyword-params :refer [wrap-keyword-params]]
              [ring.middleware.json :refer [wrap-json-response wrap-json-body wrap-json-params]]
              [ring.middleware.cors :refer [wrap-cors]]
              [clj-http.client :as client]))

  (defn- str-to [num]
    (apply str (interpose ", " (range 1 (inc num)))))
  
  (defn- str-from [num]
    (apply str (interpose ", " (reverse (range 1 (inc num))))))

  (defn- say-hello []
    (str "Hello"))

  (defn- proxy-url
    "Comment"
    [url-to-proxy]
    (:body (clj-http.client/get url-to-proxy)))
  

  (defn- process-post
    "Process the POST request JSON parameter"
    [request]
    (let [url (get-in request [:params :url])]
      ;{:status 200
      ; :body {:data (proxy-url url)
      ; :desc (str "The URL you sent was: " url)}}
      (proxy-url url)))
  
  (defroutes app-routes
    (POST "/post" request (-> request 
                                    (process-post) 
                                    (r/response) 
                                    (r/content-type "application/json")))
    (GET "/" [] (r/content-type (r/response {:helloworld "Hello World!"}) "application/json"))
    (GET "/count-up/:to" [to] (str-to (Integer. to)))
    (GET "/count-down/:from" [from] (str-from (Integer. from)))
    (route/resources "/")
    (route/not-found "Not Found"))  

  ; start with: lein ring server-headless  
  (def app
   (-> app-routes
      wrap-json-response
      wrap-keyword-params
      wrap-json-params
      (wrap-defaults (merge site-defaults {:security {:anti-forgery false} :params {:keywordize true}}))
      (wrap-cors :access-control-allow-origin [#".*"] 
                 :access-control-allow-methods [:get :post])))
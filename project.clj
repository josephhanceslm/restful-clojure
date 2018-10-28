(defproject restful-clojure "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [ring "1.7.0"]
                 [ring/ring-core "1.7.0"]
                 [ring/ring-defaults "0.3.2"]
                 [ring/ring-json "0.4.0"]
                 [ring/ring-jetty-adapter "1.7.0"]
                 [ring-cors "0.1.12"]
                 [compojure "1.6.1"]
                 [clj-http "3.9.1"]]
                 
  ; The lein-ring plugin allows us to easily start a development web server
  ; with "lein ring server". It also allows us to package up our application
  ; as a standalone .jar or as a .war for deployment to a servlet contianer
  ; (I know... SO 2005).
  :plugins [[lein-ring "0.8.10"]]
  
  ; See https://github.com/weavejester/lein-ring#web-server-options for the
  ; various options available for the lein-ring plugin
  :ring {:handler restful-clojure.handler/app
         :nrepl {:start? true
                 :port 9998}}

  :profiles
        {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                              [ring-mock "0.1.5"]]}})

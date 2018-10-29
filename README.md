# restful-clojure

Example of a Ring-based application using Compojure, ring-cors, and clj-http.

## Overview

I'm just getting started with Clojure and it's my experience that the best way to become fluent in a new language, in this case a new paradigm, is to force yourself to do real work with it.  I know some spend considerable time reading books but, for me at least, that is no substitute for struggling through it.

I have a PV installation at my home and the inverter is a [Fronius Primo](http://www.fronius.com/en/photovoltaics/products/all-products/inverters/fronius-primo/fronius-primo-3-0-1) which has a nice JSON-based [API](http://www.fronius.com/~/downloads/Solar%20Energy/Operating%20Instructions/42%2C0410%2C2012.pdf) that provides significant details about the system operation both current and over time.  My intent was to write a ClojureScript-based page to display information read from the inverter but, of course, the inverter's API doesn't support CORS and so the ClojureScript pages can't make web service calls to it directly.

This project is a simple Clojure app using Ring and some Ring middleware, including ring-cors, that takes a URL passed from the caller, queries that service, and then passes the JSON response back to the caller.  It's essentially a proxy of sorts.

The biggest problem with the entire Clojure ecosystem from a beginner's perspective is that so many of the components out there are either poorly documented or have been documented under the assumption that the reader is already an expert.  In this case terseness is NOT an asset!  So many of these simply beg for just a simple example showing how the library works.

Some challenges I faced in doing this simple project:

1) While the CORS middleware is available, there is virtually no information on how to use it.  It's used in this code and hopefully this example will help someone.
2) I used Compojure but there is no mention of how to use Ring middleware with Compojure.  It's not obvious, of course, until it is.
3) Lots of examples of how to do GET requests using clj-http.client but few on POST.  Even less on using JSON.  Both are shown here.  There are some left-over GET examples in the code, they were just for testing.
4) There is nearly nothing about the "anti-forgery" functionality but, of course, if you don't handle it nothing works!  This shows how to disable it.

One of the things I'm trying to exercise here is the thread-first `(->)` macro.  If you see yourself ending up with a deeply nested string of functions, think about one of the threading macros.  It makes for much more readable code!

Of course what would a Clojure RESTFul web service be without a companion ClojureScript SPA to call it?  That's was spawned this to begin with.  Please refer to my other project called [restful-client](https://github.com/josephhanceslm/restful-client) for that.  In much the same way that this back-end project was a struggle, the same sorts of things came up on the front-end.

Enjoy!


## Usage

Start the application with 

```
lein ring server-headless [optional port number]
```

and you should be able to POST a request like:

```
http://localhost:3000/post
```

With a JSON body parameter like:

```
{"url":"http://10.0.0.15/solar_api/v1/GetInverterRealtimeData.cgi?Scope=System"}
```

This will return JSON from the remote system's response.  In this case something like this:

```
{
   "Body" : {
      "Data" : {
         "DAY_ENERGY" : {
            "Unit" : "Wh",
            "Values" : {
               "1" : 870
            }
         },
         "PAC" : {
            "Unit" : "W",
            "Values" : {
               "1" : 2130
            }
         },
         "TOTAL_ENERGY" : {
            "Unit" : "Wh",
            "Values" : {
               "1" : 1574178
            }
         },
         "YEAR_ENERGY" : {
            "Unit" : "Wh",
            "Values" : {
               "1" : 1574178
            }
         }
      }
   },
   "Head" : {
      "RequestArguments" : {
         "DeviceClass" : "Inverter",
         "Scope" : "System"
      },
      "Status" : {
         "Code" : 0,
         "Reason" : "",
         "UserMessage" : ""
      },
      "Timestamp" : "2018-10-29T09:31:07-04:00"
   }
}
```

## License

Copyright © 2018 J.W. Hance

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

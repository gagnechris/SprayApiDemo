SprayApiDemo
============

A sample Scala/Spray API application which demonstrates how Spray can be used to create and consume RESTful APIs.  This application exposes two features; timezone and elevation.  The application uses Google APIs for the real work.

Getting Started
---------------

1. Clone the repository
2. In the base directory, start sbt
3. Compile the application by typing 'compile'
4. Run the application by typing 'run'
5. Use the api...Examples:
   a) http://localhost:8080/api/ElevationService/39/80
   b) http://localhost:8080/api/TimezoneService/39/-119/1331161200

Technologies Used
-----------------

1. Spray 1.1-M7
2. Akka 2.1.4
3. Scala 1.10.1
4. ScalaTest 2.0.M6-SNAP14 (Implementation coming soon)

What's Going On
---------------

Here is a step-by-step explaination of what I did, so that you can use this to build your own APIs using Scala/Spray.  Assumption: Use sbt, it's definitly the way to go.

1. Add spray to your ./project/plugins.sbt file.
2. Add the spray resolver and bring in the required dependencis to your build.sbt file.
3. Create the Service App.  Here we called it ./project/src/main/SprayApiServiceApp.scala.  This will create your Actor system, HttpConduit and maintain your routes.  This is also the entry point for your application.
4. Create the Service Actor, which the App we created in step 3 will hand work off to.  See ./project/src/main/ElevationService.scala as an example.  This will process the business logic.  Here we also call other services to fulfill the request.

What I like/Dislike About All This
----------------------------------

### Likes
1. Spray is based on Akka, which is very familiar
2. Spray routing is easy to use and pretty powerful
3. Spray allows you to pick what you want a la carte.  Unlike other web frameworks, you only need to take what you'll use.

### Dislikes
1. Spray documentation lacks good examples and it's difficult to fine examples online.  You are pretty much on your own.

Known Issues
------------

This demo application is a work in progress.  The TimezoneService is not fully functional because I have not been able to figure out how to get HTTPS working with Google's APIs which require you to use HTTPS instead of HTTP.

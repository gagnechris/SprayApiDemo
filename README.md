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

Here is a step-by-step explaination of what I did, so that you can use this to build your own APIs using Scala/Spray.

...

Known Issues
------------

This demo application is a work in progress.  The TimezoneService is not fully functional because I have not been able to figure out how to get HTTPS working with Google's APIs which require you to use HTTPS instead of HTTP.

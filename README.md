SprayApiDemo
============

A sample Scala/Spray API application which demonstrates how Spray can be used to create and consume RESTful APIs.  This application exposes two features; timezone and elevation.  The application uses Google APIs for the real work.

Getting Started
---------------

1. Clone the repository
2. In the base directory, start sbt
3. Compile the application by typing 'compile'
4. Run the application by typing 'run' or run the tests by typing 'test'
5. Use the api...Examples:

   a) http://localhost:8080/api/ElevationService/39/80
   
   b) http://localhost:8080/api/TimezoneService/39/-119/1331161200

Technologies Used
-----------------

1. Spray 1.2-M8
2. Akka 2.2.0-RC1
3. Scala 2.10.2
4. ScalaTest 2.0.M6
5. Sbt 0.13.0

What's Going On
---------------

Here is a step-by-step explaination of what I did, so that you can use this to build your own APIs using Scala/Spray.  Assumption: Use sbt, it's definitly the way to go.

1. Add spray to your ./project/plugins.sbt file.
2. Add the spray resolver and bring in the required dependencis to your build.sbt file.
```
  name := "SprayApiDemo"

  version := "0.1"

  scalaVersion := "2.10.2"

  scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

  resolvers ++= Seq(
    "spray repo" at "http://repo.spray.io/"
  )

  libraryDependencies ++= {
    val sprayVersion = "1.2-M8"
    val akkaVersion = "2.2.0-RC1"
    Seq(
    "io.spray" % "spray-can" % sprayVersion,
    "io.spray" % "spray-routing" % sprayVersion,
    "io.spray" % "spray-testkit" % sprayVersion,
    "io.spray" % "spray-client" % sprayVersion,
    "io.spray" %%  "spray-json" % "1.2.5",
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test",
    "ch.qos.logback" % "logback-classic" % "1.0.12",
    "org.scalatest" %% "scalatest" % "2.0.M7" % "test"
    )
  }

  seq(Revolver.settings: _*)
```
3. Create the Service App.  Here we called it ./src/main/scala/Boot.scala.  This will create your Actor system, and initialize the SprayApiDemoService class which is where our routings are maintained.  This is also the entry point for your application.
4. Write our tests.  See ./src/test/scala/ElevationServiceSpec.scala as an example.  Here were are using the FreeSpec trait from the ScalaTest library to help test our services.
5. Create the Service Actor, which the App we created in step 3 will hand work off to.  See ./project/src/main/scala/ElevationService.scala as an example.  This will process the business logic.  Here we also call other services to fulfill the request.

Known Issues
------------

This demo application is a work in progress.  The TimezoneService is not fully functional because I have not been able to figure out how to get HTTPS working with Google's APIs which require you to use HTTPS instead of HTTP.

What's on my ToDo List for this Project
---------------------------------------
1. Add additional validation to provide more helpful messages when the user submit bad parameter values (e.g. Longitude great than 180)
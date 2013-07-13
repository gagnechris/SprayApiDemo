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

1. Spray 1.1-M8
2. Akka 2.1.4
3. Scala 1.10.1
4. ScalaTest 2.0.M6-SNAP14 (Implementation coming soon)

What's Going On
---------------

Here is a step-by-step explaination of what I did, so that you can use this to build your own APIs using Scala/Spray.  Assumption: Use sbt, it's definitly the way to go.

1. Add spray to your ./project/plugins.sbt file.
    addSbtPlugin("io.spray" % "sbt-revolver" % "0.6.2")
2. Add the spray resolver and bring in the required dependencis to your build.sbt file.
    resolvers ++= Seq(
      "spray repo" at "http://repo.spray.io/"
    )

    libraryDependencies ++= {
      val sprayVersion = "1.1-M8"
      val akkaVersion = "2.1.4"
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
        "org.scalatest" %% "scalatest" % "2.0.M6-SNAP14" % "test"
      )
    }

    seq(Revolver.settings: _*)

Known Issues
------------

This demo application is a work in progress.  The TimezoneService is not fully functional because I have not been able to figure out how to get HTTPS working with Google's APIs which require you to use HTTPS instead of HTTP.

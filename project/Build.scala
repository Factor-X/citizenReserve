import play.Project._
import sbt.Keys._
import sbt._


object ApplicationBuild extends Build {

  lazy val buildVersion = "2.2.4"
  lazy val playVersion = "2.2.4"

  val appName = "citizens"
  val appVersion = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    javaCore,
    javaJdbc,
    javaJpa,
    cache,
    javaEbean,
    "com.google.inject" % "guice" % "3.0" % "test",
    "org.xhtmlrenderer" % "core-renderer" % "R8",
    "net.sf.jtidy" % "jtidy" % "r938",
    "org.apache.commons" % "commons-email" % "1.3.1",
    "commons-io" % "commons-io" % "2.3",
    "com.googlecode.ehcache-spring-annotations" % "ehcache-spring-annotations" % "1.2.0",
    "com.google.inject" % "guice" % "3.0" % "test",
    "com.google.guava" % "guava" % "14.0",
    "org.postgresql" % "postgresql" % "9.3-1101-jdbc41",
    "de.neuland-bfi" % "jade4j" % "0.4.0",
    "org.apache.commons" % "commons-lang3" % "3.1",
    "com.liferay" % "org.apache.commons.fileupload" % "1.2.2.LIFERAY-PATCHED-1",
    "com.google.code.maven-play-plugin.com.github.yeungda.jcoffeescript" % "jcoffeescript" % "1.0",
    "com.fasterxml.jackson.core" % "jackson-databind" % "2.2.2",
    "org.jasypt" % "jasypt" % "1.9.2",
    "org.apache.velocity" % "velocity" % "1.7",
    "org.apache.commons" % "commons-csv" % "1.0",
    "joda-time" % "joda-time" % "2.3",
    "org.apache.httpcomponents" % "httpclient" % "4.2.1"
  )

  lazy val angularCompileTask = TaskKey[Unit]("angular-compile", "Compile angular app")
  val angularCompileSettings = angularCompileTask := {
    new AngularCompileTask().execute()
  }

  lazy val hello = TaskKey[Unit]("Prints 'Hello World'")
  hello := println("hello world!")

  val main = play.Project(appName, appVersion, appDependencies)
    .settings(
      angularCompileSettings, resources in Compile <<= (resources in Compile).dependsOn(angularCompileTask)
    )

  javaOptions ++= Seq("-Xmx512M", "-Xmx2048M", "-XX:MaxPermSize=2048M");

}

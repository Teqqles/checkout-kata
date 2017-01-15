name := "sku-homeoffice-checkout"

version := "1.0"

scalaVersion := "2.11.8"

crossPaths := false

resolvers ++=
  Seq(
    "Sonatype Releases" at "http://oss.sonatype.org/content/repositories/releases"
  )

libraryDependencies ++=
  Seq(
    "org.scalatest" %% "scalatest" % "3.0.1" % "test",
    "org.mockito" % "mockito-all" % "1.10.19" % "test"
  )

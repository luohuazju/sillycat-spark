import sbt._
import Keys._

object ApplicationBuild extends Build {

  lazy val main = Project(id = "sillycat-spark",
    base = file("."), settings = Project.defaultSettings).settings()

}


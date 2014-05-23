name := "ALG-S"

scalaVersion := "2.11.0"

val scalazVersion = "7.1.0-M7"

libraryDependencies ++= Seq(
	"org.scalatest" %% "scalatest" % "2.1.6" % "test",
	"com.novocode" % "junit-interface" % "0.9" % "test",
	"org.scalaz" %% "scalaz-core" % scalazVersion,
	"org.scalaz" %% "scalaz-effect" % scalazVersion,
 	"org.scalaz" %% "scalaz-typelevel" % scalazVersion,
  	"org.scalaz" %% "scalaz-scalacheck-binding" % scalazVersion % "test"
  )

unmanagedSourceDirectories in Compile <++= baseDirectory { base =>
  Seq(
    base / "src/main/pat",
    base / "src/main/s99",
    base / "src/test/s99",
    base / "src/main/fp"
  )
}

retrieveManaged := true

EclipseKeys.relativizeLibs := true

scalacOptions += "-feature"

initialCommands in console := "import scalaz._, Scalaz._"
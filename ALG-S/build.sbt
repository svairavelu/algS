name := "ALG-S"

scalaVersion := "2.10.3"

val scalazVersion = "7.0.5"

libraryDependencies ++= Seq(
	"org.scalatest" %% "scalatest" % "1.9.2-SNAP2" % "test",
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
    base / "src/test/s99"
  )
}

retrieveManaged := true

EclipseKeys.relativizeLibs := true

scalacOptions += "-feature"

initialCommands in console := "import scalaz._, Scalaz._"
name := "ALG-S"

scalaVersion := "2.10.3"

libraryDependencies ++= Seq(
	"org.scalaz" %% "scalaz-core" % "7.0.5",
	"org.scalatest" %% "scalatest" % "1.9.2-SNAP2" % "test",
	"com.novocode" % "junit-interface" % "0.9" % "test"
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


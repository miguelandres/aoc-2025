plugins {
  kotlin("jvm") version "2.2.20"
  application
}

group = "dev.miguely" // A company name, for example, `org.jetbrains`
version = "1.0" // Version to assign to the built artifact

repositories { // Sources of dependencies. See 2️⃣
  mavenCentral() // Maven Central Repository. See 3️⃣
}

dependencies { // All the libraries you want to use. See 4️⃣
  // Copy dependencies' names after you find them in a repository
  testImplementation(kotlin("test")) // The Kotlin test library
}

tasks.test { // See 5️⃣
  useJUnitPlatform() // JUnitPlatform for tests. See 6️⃣
}

kotlin { // Extension for easy setup
  jvmToolchain(21) // Target version of generated JVM bytecode. See 7️⃣
}

application {
  mainClass.set("MainKt") // The main class of the application
}

tasks.register<JavaExec>("runDay") {
  group = "aoc"
  description = "Run any DayXX.kt file with main()"

  val day =
    project.findProperty("day")?.toString()
      ?: error("Pass -Pday=01 (or any number)")

  val cls = "Day${day}Kt" // Add package if needed
  mainClass.set(cls)
  classpath = sourceSets["main"].runtimeClasspath
}

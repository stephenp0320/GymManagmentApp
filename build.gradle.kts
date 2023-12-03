plugins {
    kotlin("jvm") version "1.9.0"
    // Plugin for Dokka - KDoc generating tool
    id("org.jetbrains.dokka") version "1.9.10"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
   // testImplementation("org.jetbrains.kotlin:kotlin-test:1.8.10")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.8.10")
    // dependencies for logging
    implementation("io.github.microutils:kotlin-logging:3.0.5")
    implementation("org.slf4j:slf4j-simple:2.0.9")
    implementation("com.thoughtworks.xstream:xstream:1.4.20")
    implementation("org.codehaus.jettison:jettison:1.5.4")


    // For generating a Dokka Site from KDoc
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:1.9.10")


}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}
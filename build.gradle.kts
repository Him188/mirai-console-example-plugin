plugins {
    val kotlinVersion = "1.4.20"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion

    id("net.mamoe.mirai-console") version "2.0-RC" // mirai-console version
}

mirai {
    coreVersion = "2.0-RC" // mirai-core version

    publishing {
        repo = "mirai"
        packageName = "mirai-console-example-plugin"
        override = true
    }
}

kotlin.sourceSets.all { languageSettings.useExperimentalAnnotation("kotlin.RequiresOptIn") }

group = "org.example"
version = "0.1.0"

repositories {
    mavenLocal()
    jcenter()
    mavenCentral()
}
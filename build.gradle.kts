plugins {
    kotlin("jvm") version "1.4.0"
    kotlin("plugin.serialization") version "1.4.0"
    id("com.github.johnrengelman.shadow") version "5.2.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
}

kotlin.sourceSets.all {
    languageSettings.useExperimentalAnnotation("kotlin.RequiresOptIn")
}

dependencies {
    compileOnly(kotlin("stdlib-jdk8"))
    val console = "1.0-M2-1"
    compileOnly("net.mamoe:mirai-console:$console")
    compileOnly("net.mamoe:mirai-console-pure:$console")
    compileOnly("net.mamoe:mirai-core:1.2.1")
}

kotlin.target.compilations.all {
    kotlinOptions.freeCompilerArgs += "-Xjvm-default=enable"
    kotlinOptions.jvmTarget = "1.8"
}
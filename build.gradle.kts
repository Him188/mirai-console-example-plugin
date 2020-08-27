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

    val core = "1.2.2"
    val console = "1.0-M3-1"

    compileOnly("net.mamoe:mirai-console:$console")
    compileOnly("net.mamoe:mirai-core:$core")

    testImplementation("net.mamoe:mirai-console:$console")
    testImplementation("net.mamoe:mirai-core:$core")
    testImplementation("net.mamoe:mirai-console-pure:$console")
    testImplementation(kotlin("stdlib-jdk8"))
}

kotlin.target.compilations.all {
    kotlinOptions.freeCompilerArgs += "-Xjvm-default=enable"
    kotlinOptions.jvmTarget = "1.8"
}
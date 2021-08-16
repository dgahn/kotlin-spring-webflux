plugins {
    kotlin("jvm") version "1.5.21"
    id("org.jetbrains.kotlin.plugin.noarg") version "1.5.21"

}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

apply(plugin = "org.jetbrains.kotlin.plugin.noarg")

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux:2.5.3")
    implementation("org.springframework.boot:spring-boot-starter-test:2.5.3")

    testImplementation("io.kotest:kotest-assertions-core:4.6.1")
    testImplementation("io.kotest:kotest-extensions-spring:4.4.3")
    testImplementation("io.kotest:kotest-runner-junit5:4.6.1")

}

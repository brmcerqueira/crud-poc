import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "com.crud.poc"
version = "1.0"

val ktorVersion = "1.4.0"
val exposedVersion = "0.26.2"

plugins {
    application
    kotlin("jvm") version "1.4.0"
    kotlin("kapt") version "1.4.0"
    kotlin("plugin.serialization") version "1.4.0"
    id("com.github.johnrengelman.shadow") version "4.0.3"
    id("com.palantir.docker") version "0.22.1"
}

repositories {
    mavenCentral()
    jcenter()
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<KotlinCompile>().all {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClassName = "com.crud.poc.presentation.MainKt"
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    api("com.google.dagger:dagger:2.28.3")
    kapt("com.google.dagger:dagger-compiler:2.28.3")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-serialization:$ktorVersion")
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    testImplementation(group = "junit", name = "junit", version = "4.12")
}

tasks.withType<ShadowJar> {
    archiveClassifier.set("fat")
}

docker {
    name = "my/crud-poc:$version"
    files(tasks["shadowJar"].outputs)
    noCache(true)
}

tasks["docker"].dependsOn(tasks["shadowJar"])
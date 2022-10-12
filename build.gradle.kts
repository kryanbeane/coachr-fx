import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    application
}

group = "org.setu.coachr"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.slf4j:slf4j-simple:2.0.1")
    implementation ("io.github.microutils:kotlin-logging:3.0.0")
    implementation("org.litote.kmongo:kmongo:4.7.1")
    implementation("io.github.cdimascio:dotenv-kotlin:6.3.1")
}

tasks.test {
    useJUnitPlatform()
}

sourceSets {
    getByName("test").resources.srcDir("src/test/kotlin/org/kryanbeane/coachr/console/models")
    test {
        java {
            srcDirs("src/test/kotlin/org/kryanbeane/coachr/console/models")
        }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}
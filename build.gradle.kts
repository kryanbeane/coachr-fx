import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    id("org.openjfx.javafxplugin") version "0.0.8"
    application
}

group = "org.setu.coachr"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        url = uri("https://oss.sonatype.org/content/repositories/snapshots")
    }
}

javafx {
    modules("javafx.controls", "javafx.fxml")
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.slf4j:slf4j-simple:2.0.3")
    implementation ("io.github.microutils:kotlin-logging:3.0.2")
    implementation("org.litote.kmongo:kmongo:4.7.0")
    implementation("io.github.cdimascio:dotenv-kotlin:6.3.1")
    implementation("no.tornado:tornadofx:2.0.0-SNAPSHOT")
    implementation("no.tornado:tornadofx-controlsfx:0.1.1")
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
    kotlinOptions.jvmTarget = "17"
}

application {
    mainClass.set("MainKt")
}
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "2.1.10"
    id("org.springframework.boot") version "3.3.0"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    application
}

group = "cz.sg"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

application {
    mainClass.set("cz.sg.kotlinexamples.KotlinExamplesApplication")
}

dependencies {
    runtimeOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.springframework.boot:spring-boot-starter")

    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

    testImplementation("org.spockframework:spock-bom:2.3-groovy-3.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

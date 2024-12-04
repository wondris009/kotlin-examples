import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.1"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
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
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.springframework.boot:spring-boot-starter")

    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

    testImplementation("org.spockframework:spock-bom:2.3-groovy-3.0")
//    testImplementation("org.spockframework:spock-spring")
//    testImplementation("org.spockframework:spock-junit4")
//    testRuntimeOnly 'net.bytebuddy:byte-buddy' // required by Spock to mock classes
//    testRuntimeOnly 'org.junit.vintage:junit-vintage-engine' // to run all junit4 tests


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

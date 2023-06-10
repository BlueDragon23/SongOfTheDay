plugins {
    id("application")
    id("java")
    id("org.springframework.boot") version "3.1.0"
    id("com.diffplug.spotless") version "6.19.0"
}

group = "sotd"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val jacksonVersion = "2.15.2"
val springVersion = "3.1.0"

dependencies {
    // https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core
    implementation("com.fasterxml.jackson.core:jackson-core:$jacksonVersion")
    // https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-annotations
    implementation("com.fasterxml.jackson.core:jackson-annotations:$jacksonVersion")
    // https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8:$jacksonVersion")
    // https://mvnrepository.com/artifact/io.github.openfeign/feign-core
    implementation("io.github.openfeign:feign-core:11.5")
    // https://mvnrepository.com/artifact/io.github.openfeign/feign-jackson
    implementation("io.github.openfeign:feign-jackson:11.5")

    implementation(platform("org.springframework.boot:spring-boot-dependencies:$springVersion"))
    implementation("org.springframework.boot:spring-boot-starter-web:$springVersion")

    // Tests
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
    testImplementation("org.assertj:assertj-core:3.22.0")
}

spotless {
    java {
        // TODO: fix palantir java format. It doesn't work on WSL, as it looks for a .exe Java
        // see https://github.com/palantir/palantir-java-format/issues/721 which broke WSL
        palantirJavaFormat()
    }
}

tasks {
    test {
        useJUnitPlatform()
    }
}
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
val junitVersion = "5.9.2"

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
    implementation("io.github.openfeign.form:feign-form:3.8.0")

    implementation(platform("org.springframework.boot:spring-boot-dependencies:$springVersion"))
    implementation("org.springframework.boot:spring-boot-starter-web:$springVersion")

    // Tests
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testImplementation("org.assertj:assertj-core:3.22.0")
    testImplementation("com.github.tomakehurst:wiremock:3.0.0-beta-9")
    testImplementation("org.springframework.boot:spring-boot-starter-test:$springVersion")
    testImplementation("org.springframework.cloud:spring-cloud-starter-contract-stub-runner:4.0.3")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
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
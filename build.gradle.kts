plugins {
    id("org.springframework.boot") version "3.5.2"
    id("java")
}

group = "chenjun.test"
version = "1.0-SNAPSHOT"

repositories {
    maven {
        url = uri("https://maven.aliyun.com/repository/public/")
    }
    maven {
        url = uri("https://maven.aliyun.com/repository/spring/")
    }
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-security:3.5.2")
    implementation("org.springframework.boot:spring-boot-starter-web:3.5.2")
    implementation("org.springframework.kafka:spring-kafka:3.3.7")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.9")
    implementation (platform("org.springframework.ai:spring-ai-bom:1.0.0"))
    implementation ("org.springframework.ai:spring-ai-starter-model-deepseek:1.0.0")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.withType<Wrapper> {
    gradleVersion = "8.14.2"
    distributionUrl = "https://mirrors.cloud.tencent.com/gradle/gradle-8.14.2-bin.zip"
}
tasks.test {
    useJUnitPlatform()
}
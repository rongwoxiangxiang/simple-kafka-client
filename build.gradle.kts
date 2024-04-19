plugins {
  id("java")
//  id("org.jetbrains.kotlin.jvm") version "1.9.21"
  id("org.jetbrains.intellij") version "1.16.1"
}

group = "cn.clscls.plugin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
//    mavenLocal()
//    maven("https://www.jetbrains.com/intellij-repository/releases")
//    maven("https://cache-redirector.jetbrains.com/intellij-dependencies")

}

intellij {
    version.set("2022.1.2")
    type.set("IC") // Target IDE Platform

    plugins.set(
        listOf(
            "org.intellij.intelliLang",
            "com.intellij.java",
//            "org.jetbrains.kotlin",
//            "com.intellij.properties",
//            "org.jetbrains.plugins.yaml"
        )
    )
}

dependencies {
    implementation("org.netbeans.external:org-apache-commons-lang3:RELEASE130")
//    implementation("org.netbeans.external:org-apache-commons-lang:RELEASE113")
//    implementation("cn.hutool:hutool-all:5.8.25")
    implementation("com.fasterxml.jackson.core:jackson-core:2.16.0")
//    implementation("redis.clients:jedis:5.1.0")
    implementation("org.slf4j:slf4j-api:2.0.9")
    implementation("org.apache.kafka:kafka-clients:2.6.3")
    implementation("org.springframework.kafka:spring-kafka:2.3.5.RELEASE")
//    implementation("org.apache.commons:commons-text:1.11.0")
//    compileOnly("org.projectlombok:lombok:1.18.20")
//    annotationProcessor("org.projectlombok:lombok:1.18.20")

//    implementation("org.reflections:reflections:0.10.2") {
//        exclude(group = "org.slf4j")
//    }
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "11"
        targetCompatibility = "11"
    }
//    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
//        kotlinOptions.jvmTarget = "11"
//    }

    patchPluginXml {
        sinceBuild.set("212")
        untilBuild.set("241.*")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}

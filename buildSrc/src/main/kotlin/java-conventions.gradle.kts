@file:Suppress("UnstableApiUsage")

import com.volalm15.gradle.constant.JDK_VERSION

plugins {
    `java-library`
}

java {
    // Auto JDK setup
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(JDK_VERSION))
    }
    withSourcesJar()
    withJavadocJar()
}

tasks.compileJava {
    // See: https://docs.oracle.com/en/java/javase/12/tools/javac.html
    @Suppress("SpellCheckingInspection")
    options.compilerArgs.addAll(
        listOf(
            "-Xlint:all", // Enables all recommended warnings.
            "-Werror" // Terminates compilation when warnings occur.
        )
    )
    options.encoding = "UTF-8"
}

tasks.jar {
    enabled = false
}

repositories {
    mavenCentral()
    maven("https://repo.spring.io/snapshot")
    maven("https://repo.spring.io/milestone")
}

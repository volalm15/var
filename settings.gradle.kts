plugins {
    id("com.gradle.enterprise") version ("3.10.3")
}

rootProject.name = "var"
include(":config-service")
include(":discovery-service")
include(":edge-service")


if (!System.getenv("CI").isNullOrEmpty() && !System.getenv("BUILD_SCAN_TOS_ACCEPTED").isNullOrEmpty()) {
    gradleEnterprise {
        buildScan {
            termsOfServiceUrl = "https://gradle.com/terms-of-service"
            termsOfServiceAgree = "yes"
            tag("CI")
        }
    }
}

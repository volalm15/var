plugins {
	id("kotlin-conventions")
	id("testing-conventions")
	id("dokka-conventions")
	id("spring-conventions")
}

val j2htmlVersion: String by rootProject.extra
val kotlinLoggingVersion: String by rootProject.extra

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.cloud:spring-cloud-config-server")
	implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
	implementation("io.micrometer:micrometer-registry-prometheus")
	implementation("io.micrometer:micrometer-tracing-bridge-brave")
	implementation("io.zipkin.reporter2:zipkin-reporter-brave")
	implementation(libs.bundles.j2html)
	implementation(libs.bundles.kotlinLogging)

	developmentOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

dependencyManagement {
	imports {
		mavenBom(libs.spring.cloud.bom.get().toString())
	}
}

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
	implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.boot:spring-boot-starter-aop")

	implementation("org.springframework.cloud:spring-cloud-starter")
	implementation("org.springframework.cloud:spring-cloud-starter-config")
	implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
	implementation("org.springframework.retry:spring-retry")

	implementation(libs.bundles.j2html)
	implementation(libs.bundles.kotlinLogging)


	developmentOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
}

dependencyManagement {
	imports {
		mavenBom(libs.spring.cloud.bom.get().toString())
	}
}

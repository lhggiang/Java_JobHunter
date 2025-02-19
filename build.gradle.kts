plugins {
	java
	id("org.springframework.boot") version "3.2.4"
	id("io.spring.dependency-management") version "1.1.4"
	id("io.freefair.lombok") version "8.6"
	id("jacoco")
}

group = "vn.hoanggiang"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-mail")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
	implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")
	implementation("com.turkraft.springfilter:jpa:3.1.7")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")
	implementation("org.springframework.cloud:spring-cloud-starter-openfeign:4.1.0")

	// Logstash Logback Encoder
	implementation("net.logstash.logback:logstash-logback-encoder:7.2")

	// SLF4J Logger (API for logging)
	implementation("org.slf4j:slf4j-api:2.0.16")

	// Redis
	implementation("org.springframework.data:spring-data-redis")
	implementation("redis.clients:jedis:5.1.3")

	// PDFBox
	implementation("org.apache.pdfbox:pdfbox:3.0.3") {
		exclude(group = "commons-logging", module = "commons-logging")
	}

	// Development and runtime dependencies
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("com.mysql:mysql-connector-j")

	// Testing
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
}

jacoco {
	toolVersion = "0.8.12"
}

tasks.test {
	useJUnitPlatform()
	finalizedBy("jacocoTestReport")
}

tasks.named<JacocoReport>("jacocoTestReport") {
	dependsOn(tasks.test)
	reports {
		xml.required.set(true)
		html.required.set(true)
		csv.required.set(true)
	}
}

tasks.named<JacocoReport>("jacocoTestReport") {
	classDirectories.setFrom(
			files(
					fileTree("build/classes/java/main") {
						exclude("vn/hoanggiang/jobhunter/config/**",
								"vn/hoanggiang/jobhunter/domain/**",
								"vn/hoanggiang/jobhunter/repository/**",
								"vn/hoanggiang/jobhunter/util/**")
					}
			)
	)
	reports {
		xml.required.set(true)
		html.required.set(true)
		csv.required.set(false)
	}
}

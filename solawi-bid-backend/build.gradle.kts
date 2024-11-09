
plugins {
    application
    alias(libs.plugins.jvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.serialization)
    alias(libs.plugins.shadow)
    //id("com.github.johnrengelman.shadow") version "8.1.0"
    id("org.evoleq.exposedx.migration")
    //alias(libs)
    // id("jacoco") // JaCoCo plugin
}

group = libs.versions.solytonGroup
version = libs.versions.solawi
val solawiBackendMainClassName = "org.solyton.solawi.bid.MainKt"
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

kotlin{
    jvmToolchain(17)
}

application {
    //mainClass = solawiBackendMainClassName
    mainClass.set(solawiBackendMainClassName)

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://maven.pkg.jetbrains.space/public/p/ktor/eap")
}

dependencies {
    // ktor
    implementation(libs.ktor.server.core.jvm)
    implementation(libs.ktor.server.netty.jvm)
    implementation(libs.ktor.server.cors)
    implementation(libs.ktor.server.content.negotiation)
    implementation("io.ktor:ktor-server-auth:2.3.12") // Replace with the latest Ktor version
    implementation("io.ktor:ktor-server-auth-jwt:2.3.12")
//    implementation("io.ktor:ktor-auth:2.3.12")
//    implementation("io.ktor:ktor-auth-jwt:2.3.12")
    implementation("com.auth0:java-jwt:4.0.0")
    implementation(libs.logback)
    testImplementation(libs.ktor.server.tests.jvm)
    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.junit.jupiter)

    // bcrypt
    implementation ("org.mindrot:jbcrypt:0.4")

    // jwt
    implementation ("io.jsonwebtoken:jjwt-api:0.11.5") // JWT API
    implementation ("io.jsonwebtoken:jjwt-impl:0.11.5") // JWT implementation
    implementation ("io.jsonwebtoken:jjwt-jackson:0.11.5") // JWT Jackson support (for JSON processing)


    implementation("io.github.cdimascio:dotenv-kotlin:6.4.1")

    // own dependencies
    //implementation("org.solyton:solawi-bid-api-data-jvm:0.0.1")
    api(project(":solawi-bid-api-data"))

    // serialization
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.kotlinx.serialization.json)

    // exposed
    implementation(libs.exposed.core)
    implementation(libs.exposed.crypt)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.jdbc)
    implementation(libs.exposed.joda.time)
    // mysql connector
    implementation(libs.mysql.connector.java)

    // h2
    implementation(libs.h2)

    // slf4j
    implementation (libs.slf4j.nop)
}
/*
jacoco {
    toolVersion = "0.8.8" // Specify JaCoCo version
}

 */



tasks.register<Test>("dbFunctionalTest"  ) {
    group = "verification"
    useJUnitPlatform() {
        includeTags("dbFunctional")
    }
    reports {
        junitXml.required = true
        html.required = true

    }
//    finalizedBy(tasks.jacocoTestReport)
}

tasks.register<Test>("apiTest") {
    group = "verification"
    useJUnitPlatform() {
        includeTags("api")
    }
    reports {
        junitXml.required = true
        html.required = true
    }
//    finalizedBy(tasks.jacocoTestReport)
}

tasks.register<Test>("unitTest") {
    group = "verification"
    useJUnitPlatform() {
        includeTags("unit")

    }
    reports {
        junitXml.required = true
        html.required = true
    }
//    finalizedBy(tasks.jacocoTestReport)
}
tasks.register<Test>("schemaTest") {
    group = "verification"
    useJUnitPlatform() {
        includeTags("schema")

    }
    reports {
        junitXml.required = true
        html.required = true
    }
//    finalizedBy(tasks.jacocoTestReport)
}

tasks.register<Test>("testFrameworkTest") {
    group = "verification"
    useJUnitPlatform() {
        includeTags("testFramework")

    }
    reports {
        junitXml.required = true
        html.required = true
    }
//    finalizedBy(tasks.jacocoTestReport)
}

/*
tasks.jacocoTestReport {
    reports {

        xml.isEnabled = false // Disable XML report
        csv.isEnabled = false // Disable CSV report
        html.isEnabled = true  // Enable HTML report
    }
}


 */

migrations {
    migration("dbMain") {
        domain = "org.solyton.solawi.bid"
        module = "db"
        migrations = "migrations"
        //"main"
    }

    migration("dbSchemaTest") {
        domain = "org.solyton.solawi.bid"
        module = "db/schema"
        migrations = "migrations"
        sourceSet = "test"
    }

    migration("bidApiTest") {
        domain = "org.solyton.solawi.bid"
        module = "bid/routing"
        migrations = "migrations"
        sourceSet = "test"
    }

    migration("authenticationApiTest") {
        domain = "org.solyton.solawi.bid"
        module = "authentication/routing"
        migrations = "migrations"
        sourceSet = "test"
    }
}


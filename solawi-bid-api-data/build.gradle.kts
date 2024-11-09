plugins {
    alias(libs.plugins.mpp)
    alias(libs.plugins.serialization)
    //kotlin("multiplatform") version "1.9.22"
    //kotlin("plugin.serialization") version "1.9.22"
     `maven-publish`
}



group = libs.versions.solytonGroup// "org.solyton"//project.extra["solawiBid.group"] as String
version = libs.versions.solawi// versions"0.0.1"//project.extra["solawiBid.version"] as String
//val ktorVersion: String by project
val kotlinVersion = libs.versions.kotlin // : String by project

repositories {
    mavenCentral()
}




configurations.all {
    exclude(group = "org.gradle.api.plugins", module = "MavenPlugin")
}

kotlin {
    jvmToolchain(17)
}
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}


kotlin{
    js(IR) {
        browser()
        binaries.executable()
    }
    jvm(){ }
    sourceSets {
        val commonMain by getting {
            kotlin.srcDir("/src/commonMain/kotlin")

            dependencies {
                implementation("com.benasher44:uuid:0.6.0")
                implementation(libs.kotlinx.coroutines.core)
                //implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinSerializationVersion")
                implementation(libs.kotlinx.serialization.json)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test")) // Adds kotlin.test for multiplatform
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8") // Specific for JVM
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test")) // Adds kotlin.test for multiplatform
            }
        }
        val jsMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-html-js:0.8.0") // Example for JS
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test")) // Adds kotlin.test for multiplatform
            }
        }

    }
}

publishing {
    publications {
        withType<MavenPublication>()
    }
    repositories {
        mavenLocal()
    }
}

/*
dependencies {



    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

 */

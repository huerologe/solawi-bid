


rootProject.name = "solawi-bid"

//include(":solawi-bid-frontend")
include(":solawi-bid-database")

include(":solawi-bid-api-data")

include(":solawi-bid-backend")

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven ("https://maven.pkg.jetbrains.space/public/p/ktor/eap")
    }

    plugins {
        //kotlin("multiplatform").version(extra["kotlin.version"] as String) apply false
        //id("org.jetbrains.compose").version(extra["compose.version"] as String) apply false
        //kotlin("plugin.serialization").version(extra["kotlin.version"] as String) apply false
        //kotlin("jvm").version(extra["kotlin.version"] as String) apply false
        //id("io.ktor.plugin").version(extra["ktorVersion"] as String) apply false
        // id("com.github.johnrengelman.shadow").version(extra["shadowVersion"] as String) apply false
    }


}

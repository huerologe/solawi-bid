@file:Suppress("UnstableApiUsage")

// rootProject.name = "solawi-bid"

dependencyResolutionManagement {
    // repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS

    repositories {
        mavenCentral()
        mavenLocal()
        gradlePluginPortal()
       // maven("https://maven.pkg.jetbrains.space/public/p/ktor/eap")
    }

    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}


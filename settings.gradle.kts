@file:Suppress("UnstableApiUsage")
pluginManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
        maven(url = uri("https://oss.sonatype.org/content/repositories/snapshots/"))
    }
}

include(":app")
include(":core-data")
include(":core-model")
include(":lint-checks")
include(":core-database")
include(":core-datastore")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

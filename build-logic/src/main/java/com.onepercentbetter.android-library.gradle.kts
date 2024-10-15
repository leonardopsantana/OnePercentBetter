@file:Suppress("UnstableApiUsage")

import extensions.dokkaPlugin
import extensions.getLibrary
import extensions.setupAndroidDefaultConfig
import extensions.setupCompileOptions
import extensions.setupNameSpace
import extensions.setupPackingOptions

val libs: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.onepercentbetter.dokka")
    id("org.jetbrains.kotlinx.kover")
    id("com.onepercentbetter.detekt")
}

android {
    setupNameSpace(project)

    setupCompileOptions()

    setupPackingOptions()

    setupAndroidDefaultConfig()
    defaultConfig.targetSdk = Config.targetSdkVersion

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles("proguard-android.txt", "proguard-rules.pro")
            consumerProguardFiles("proguard-rules.pro")
        }

        getByName("debug") {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    dokkaPlugin(libs.getLibrary("dokka"))
}

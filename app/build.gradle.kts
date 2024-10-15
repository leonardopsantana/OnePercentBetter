plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlinter)
}

android {
    namespace = "com.onepercentbetter"
}

dependencies {
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    debugImplementation(composeBom)
    androidTestImplementation(composeBom)

    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.google.truth)
    androidTestImplementation(libs.hilt.android.testing)
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.1.2")
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
    implementation(libs.accompanist.navigation.animation)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material3.windowsizeclass)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)

    implementation(libs.androidx.ktx.core)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.material)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.window)
    implementation(libs.bundles.accompanist)
    implementation(libs.compose.destinations.animations.core)
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    implementation(project(":core-data"))
    implementation(project(":core-model"))
    implementation(project(":core-database"))
    implementation(project(":core-datastore"))
    kapt(libs.hilt.compiler)
    kaptAndroidTest(libs.hilt.android.compiler)
    ksp(libs.androidx.room.compiler)
    ksp(libs.compose.destinations.ksp)
    lintChecks(project(":lint-checks"))
    testImplementation(libs.cash.turbine)
    testImplementation(libs.google.truth)
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)
    testImplementation(libs.viewmodel.test)
}

tasks.named("lintKotlinDebug") {
    mustRunAfter("kspDebugKotlin")
}

tasks.named("lintKotlinRelease") {
    mustRunAfter("kspReleaseKotlin")
}

tasks.lintKotlinDebug {
    exclude { it.file.path.contains("build/")}
}

tasks.lintKotlinRelease {
    exclude { it.file.path.contains("build/")}
}

tasks.formatKotlinDebug {
    exclude { it.file.path.contains("build/")}
}

tasks.formatKotlinRelease {
    exclude { it.file.path.contains("build/")}
}

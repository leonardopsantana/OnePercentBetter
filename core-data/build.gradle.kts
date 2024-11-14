plugins {
    id("com.onepercentbetter.android-library")
    id("com.google.devtools.ksp")
}

dependencies {
    implementation(projects.coreModel)
    implementation(projects.coreDatabase)
    implementation(projects.coreDatastore)
    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
}

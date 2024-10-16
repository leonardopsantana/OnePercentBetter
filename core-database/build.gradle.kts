plugins {
    id("com.onepercentbetter.android-library")
    id("com.google.devtools.ksp")
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // database
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    testImplementation(libs.viewmodel.test)

    // di
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // unit test
    testImplementation(libs.junit)
}

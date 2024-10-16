import com.google.protobuf.gradle.id
import com.google.protobuf.gradle.protobuf

plugins {
    id("com.onepercentbetter.android-library")
    id("com.google.devtools.ksp")
    id("com.google.protobuf").version("0.9.4")
}

dependencies {
    implementation(project(":core-model"))

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //datastore
    implementation(libs.androidx.datastore)

    // di
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    //protobuf
    implementation(libs.google.protobuf.javalite)
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.21.12"
    }

    // Generates the java Protobuf-lite code for the Protobufs in this project. See
    // https://github.com/google/protobuf-gradle-plugin#customizing-protobuf-compilation
    // for more information.
    generateProtoTasks {
        all().forEach {
            it.builtins {
                id("java") {
                    option("lite")
                }
            }
        }
    }
}

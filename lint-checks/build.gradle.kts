plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$rootProject.ext.versions.kotlinVersion")
    compileOnly("com.android.tools.lint:lint-api:31.3.1")
    compileOnly("com.android.tools.lint:lint-checks:31.3.1")

    testImplementation("com.android.tools.lint:lint-tests:27.1.2")
    testImplementation("junit:junit:$rootProject.ext.versions.junit")
}

tasks.withType<Jar> {
    manifest {
        attributes["Lint-Registry-v2"] = "com.onepercentbetter.lint.checks.LintRegistry"
    }
}

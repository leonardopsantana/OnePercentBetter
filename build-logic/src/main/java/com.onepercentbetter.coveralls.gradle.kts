plugins {
    id("com.github.kt3k.coveralls")
}

coveralls {
    jacocoReportPath = "${rootProject.projectDir}/app/build/reports/jacoco/combinedJacocoTestReport/combinedJacocoTestReport.xml"
}

tasks.coveralls {
    dependsOn 'combinedJacocoTestReport'
}

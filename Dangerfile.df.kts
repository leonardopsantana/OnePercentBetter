@file:DependsOn("xyz.pavelkorolev.danger.detekt:plugin:1.2.0")
@file:Suppress("MagicNumber", "WildcardImport", "ForbiddenComment")

// Editing this file: https://github.com/danger/kotlin?tab=readme-ov-file#autocomplete-and-syntax-highlighting-in-intellij-idea-or-android-studio
import systems.danger.kotlin.*
import java.io.File
import systems.danger.kotlin.models.github.*
import xyz.pavelkorolev.danger.detekt.DetektPlugin

danger(args) {
    warnDetekt()
    onGitHub {
        val additions = pullRequest.additions ?: 0
        val deletions = pullRequest.deletions ?: 0

        message("Thanks @${pullRequest.user.login}!")

        if (pullRequest.body.isNullOrBlank()) {
            fail("Please provide a summary in the Pull Request description.")
        }

        if (additions > 500) {
            warn("Please consider breaking up this pull request.")
        }

        if (issue.labels.isEmpty()) {
            warn("Please add labels to this PR.")
        }

        if (deletions > additions) {
            message("🎉 Code Cleanup!")
        }

        if ("WIP" in pullRequest.title) {
            warn(
                ":construction: PR is marked with Work in Progress (WIP)",
            )
        }
    }
}

fun warnDetekt() {
    val file = File("build/reports/detekt/report.sarif")
    if (!file.exists()) {
        warn(
            "🙈 No detekt report found",
        )
        return
    }
    with(DetektPlugin) {
        val report = parse(file)
        val count = report.count
        if (count == 0) {
            message("👏👏👏 Good job! Detekt found no violations here!")
            return
        }
        fail(
            "🙁 Detekt violations found: **${report.count}**.\n" +
                "Please fix them to proceed. We have zero-warning policy"
        )
        report(report)
    }
}

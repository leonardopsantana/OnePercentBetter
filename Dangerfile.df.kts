@file:DependsOn("io.github.ackeecz:danger-kotlin-detekt:0.1.4")  
@file:Suppress("MagicNumber", "WildcardImport", "ForbiddenComment")

import systems.danger.kotlin.*
import java.io.File
import io.github.ackeecz.danger.detekt.DetektPlugin  
import systems.danger.kotlin.*  

register.plugin(DetektPlugin)  
  
danger(args) {  
    warnDetekt()  

    onGitHub {
            warnWorkInProgress()
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
        }
}  
  
fun warnDetekt() {  
    val detektReport = File("build/reports/detekt/report.xml")  
    if (!detektReport.exists()) {  
        warn(  
            ":see_no_evil: No detekt report found",  
        )  
        return  
    }  
    DetektPlugin.parseAndReport(detektReport)  
}  
  
fun GitHub.warnWorkInProgress() {  
    if ("WIP" in pullRequest.title) {  
        warn(  
            ":construction: PR is marked with Work in Progress (WIP)",  
        )  
    }  
}

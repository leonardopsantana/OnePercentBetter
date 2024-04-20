package com.onepercentbetter.addtask.domain.model

/**
 * A collection of possible results for an attempt to login the user.
 */
sealed class AddTaskResult {
    object Success : AddTaskResult()

    sealed class Failure : AddTaskResult() {
        data class InvalidInput(
            val emptyDescription: Boolean,
            val scheduledDateInPast: Boolean,
        ) : AddTaskResult()

        object Unknown : Failure()
    }
}

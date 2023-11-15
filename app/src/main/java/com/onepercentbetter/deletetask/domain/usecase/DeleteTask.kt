package com.onepercentbetter.deletetask.domain.usecase

import com.onepercentbetter.core.data.Result
import com.onepercentbetter.tasklist.domain.model.Task

/**
 * Given a new task, delete any reference of that in the user's task list.
 */
interface DeleteTask {

    suspend operator fun invoke(task: Task): Result<Unit>
}
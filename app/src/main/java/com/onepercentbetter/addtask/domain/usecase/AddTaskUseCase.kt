package com.onepercentbetter.addtask.domain.usecase

import com.onepercentbetter.core.data.Result
import com.onepercentbetter.tasklist.domain.model.Task

/**
 * Given a new task, store that in the user's task list.
 */
interface AddTaskUseCase {

    suspend operator fun invoke(task: Task): Result<Unit>
}
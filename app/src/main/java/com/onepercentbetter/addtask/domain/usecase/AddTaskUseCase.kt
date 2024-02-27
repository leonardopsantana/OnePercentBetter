package com.onepercentbetter.addtask.domain.usecase

import com.onepercentbetter.addtask.domain.model.AddTaskResult
import com.onepercentbetter.core_model.Task

/**
 * Given a new task, store that in the user's task list.
 */
interface AddTaskUseCase {

    suspend operator fun invoke(task: com.onepercentbetter.core_model.Task): AddTaskResult
}

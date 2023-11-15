package com.onepercentbetter.tasklist.domain.usecases

import com.onepercentbetter.core.data.Result
import com.onepercentbetter.tasklist.domain.model.Task

/**
 * Given a task, mark it as completed so it's no longer shown in the todo list.
 */
interface MarkTaskAsCompleteUseCase {

    suspend operator fun invoke(): Result<List<Task>>
}
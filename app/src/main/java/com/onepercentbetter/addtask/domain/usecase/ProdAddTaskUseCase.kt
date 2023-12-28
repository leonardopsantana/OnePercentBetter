package com.onepercentbetter.addtask.domain.usecase

import com.onepercentbetter.core.data.Result
import com.onepercentbetter.tasklist.domain.model.Task
import com.onepercentbetter.tasklist.domain.repository.TaskListRepository
import javax.inject.Inject

class ProdAddTaskUseCase @Inject constructor(
    private val taskListRepository: TaskListRepository
) : AddTaskUseCase {
    override suspend fun invoke(task: Task): Result<Unit> {
        return taskListRepository.addTask(task)
    }
}
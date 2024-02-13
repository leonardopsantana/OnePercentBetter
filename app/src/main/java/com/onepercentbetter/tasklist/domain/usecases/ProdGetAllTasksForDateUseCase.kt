package com.onepercentbetter.tasklist.domain.usecases

import com.onepercentbetter.tasklist.domain.repository.TaskRepository
import com.onepercentbetter.tasklist.domain.repository.TaskListResult
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class ProdGetAllTasksForDateUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) : GetTasksForDateUseCase {
    override fun invoke(date: LocalDate): Flow<TaskListResult> {
        return taskRepository.fetchTasksForDate(date)
    }
}

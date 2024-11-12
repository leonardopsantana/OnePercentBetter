package com.onepercentbetter.tasklist.domain.usecases

import com.onepercentbetter.core.model.toEpochMillis
import com.onepercentbetter.repository.task.TaskListResult
import com.onepercentbetter.repository.task.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combineTransform
import java.time.LocalDate
import javax.inject.Inject

class ProdGetTasksForDateUseCase
    @Inject
    constructor(
        private val taskRepository: TaskRepository,
    ) : GetTasksForDateUseCase {
        override fun invoke(
            date: LocalDate,
        ): Flow<TaskListResult> {
            val dateMillis = date.toEpochMillis()

            val incompleteTaskFlow = taskRepository.fetchTasksForDate(dateMillis, completed = false)
            val completeTaskFlow = taskRepository.fetchTasksForDate(dateMillis, completed = true)

            return incompleteTaskFlow.combineTransform(completeTaskFlow) { incomplete, complete ->
                val incompleteTasks = incomplete.getOrNull()
                val completeTasks = complete.getOrNull()

                if (incompleteTasks != null && completeTasks != null) {
                    val result = Result.success(incompleteTasks + completeTasks)
                    emit(result)
                } else {
                    emit(Result.failure(Throwable("Error requesting tasks for date: $date")))
                }
            }
        }
    }

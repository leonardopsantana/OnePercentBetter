package com.onepercentbetter.tasklist.domain.usecases

import com.onepercentbetter.core.data.Result
import com.onepercentbetter.task.TaskListResult
import com.onepercentbetter.task.api.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combineTransform
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

class ProdGetTasksForDateUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) : GetTasksForDateUseCase {
    override fun invoke(
        date: LocalDate
    ): Flow<TaskListResult> {
        val dateMillis = date.atStartOfDay()
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()

        val incompleteTaskFlow = taskRepository.fetchTasksForDate(dateMillis, completed = false)
        val completeTaskFlow = taskRepository.fetchTasksForDate(dateMillis, completed = true)

        return incompleteTaskFlow.combineTransform(completeTaskFlow) { incomplete, complete ->
            if (incomplete is Result.Success && complete is Result.Success) {
                val result = Result.Success(incomplete.data + complete.data)
                emit(result)
            } else {
                emit(Result.Error(Throwable("Error requesting tasks for date: $date")))
            }
        }
    }
}

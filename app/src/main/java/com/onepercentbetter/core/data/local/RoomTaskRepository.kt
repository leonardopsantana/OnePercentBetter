package com.onepercentbetter.core.data.local

import com.onepercentbetter.core_data.Result
import com.onepercentbetter.core_model.Task
import com.onepercentbetter.tasklist.domain.repository.TaskListResult
import com.onepercentbetter.tasklist.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class RoomTaskRepository @Inject constructor(
    private val taskDAO: TaskDAO
) : TaskRepository {
    override fun fetchAllTasks(): Flow<Result<List<Task>>> {
        return taskDAO
            .fetchAllTasks()
            .map { taskList ->
                Result.Success(taskList.toDomainTaskList())
            }
    }

    override fun fetchTasksForDate(date: LocalDate, completed: Boolean): Flow<TaskListResult> {
        return taskDAO
            .fetchTasksForDate(date.toPersistableDateString(), completed)
            .map { taskList ->
                Result.Success(taskList.toDomainTaskList())
            }
    }

    override suspend fun addTask(task: com.onepercentbetter.core_model.Task): Result<Unit> {
        taskDAO.insertTask(task.toPersistableTask())

        return Result.Success(Unit)
    }

    override suspend fun deleteTask(task: com.onepercentbetter.core_model.Task): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun updateTask(task: com.onepercentbetter.core_model.Task): Result<Unit> {
        taskDAO.updateTask(task.toPersistableTask())

        return Result.Success(Unit)
    }
}

private fun List<PersistableTask>.toDomainTaskList(): List<com.onepercentbetter.core_model.Task> {
    return this.map(PersistableTask::toTask)
}

private const val PERSISTED_DATE_FORMAT = "dd-MM-yyyy"
private val persistedDateFormatter = DateTimeFormatter.ofPattern(PERSISTED_DATE_FORMAT)

private fun LocalDate.toPersistableDateString(): String {
    return persistedDateFormatter.format(this)
}

private fun PersistableTask.toTask(): com.onepercentbetter.core_model.Task {
    return com.onepercentbetter.core_model.Task(
        id = this.id,
        description = this.description,
        scheduledDateMillis = this.scheduledDate,
        completed = this.completed
    )
}

private fun com.onepercentbetter.core_model.Task.toPersistableTask(): PersistableTask {
    return PersistableTask(
        id = this.id,
        description = this.description,
        scheduledDate = this.scheduledDateMillis,
        completed = this.completed
    )
}

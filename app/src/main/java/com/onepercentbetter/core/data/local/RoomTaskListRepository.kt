package com.onepercentbetter.core.data.local

import com.onepercentbetter.core.data.Result
import com.onepercentbetter.tasklist.domain.model.Task
import com.onepercentbetter.tasklist.domain.repository.TaskListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class RoomTaskListRepository @Inject constructor(
    private val taskDAO: TaskDAO
) : TaskListRepository {
    override fun fetchAllTasks(): Flow<Result<List<Task>>> {
        return taskDAO
            .fetchAllTasks()
            .map { taskList ->
                Result.Success(taskList.toDomainTaskList())
            }
    }

    override suspend fun addTask(task: Task): Result<Unit> {
        taskDAO.insertTask(task.toPersistableTask())

        return Result.Success(Unit)
    }

    override suspend fun deleteTask(task: Task): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun markAsComplete(task: Task): Result<Unit> {
        TODO("Not yet implemented")
    }
}

private fun List<PersistableTask>.toDomainTaskList(): List<Task> {
    return this.map(PersistableTask::toTask)
}

private const val PERSISTED_DATE_FORMAT = "dd-MM-yyyy"
private val persistedDateFormatter = DateTimeFormatter.ofPattern(PERSISTED_DATE_FORMAT)

private fun PersistableTask.toTask(): Task {
    return Task(
        id = this.id,
        description = this.description,
        scheduledDate = LocalDate.parse(this.scheduledDate, persistedDateFormatter)
    )
}

private fun Task.toPersistableTask(): PersistableTask {
    return PersistableTask(
        id = this.id,
        description = this.description,
        scheduledDate = persistedDateFormatter.format(this.scheduledDate)
    )
}

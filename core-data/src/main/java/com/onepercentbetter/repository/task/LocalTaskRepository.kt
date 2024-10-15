package com.onepercentbetter.repository.task

import com.onepercentbetter.core.database.entity.PersistableTask
import com.onepercentbetter.core.model.Task
import com.onepercentbetter.core.model.toEpochMillis
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun List<PersistableTask>.toDomainTaskList(): List<Task> {
    return this.map(PersistableTask::toTask)
}

private const val PERSISTED_DATE_FORMAT = "yyyy-MM-dd"
private val persistedDateFormatter = DateTimeFormatter.ofPattern(PERSISTED_DATE_FORMAT)

fun LocalDate.toPersistableDateString(): String {
    return persistedDateFormatter.format(this)
}

private fun PersistableTask.toTask(): Task {
    return Task(
        id = this.id,
        description = this.description,
        scheduledDateMillis =
            LocalDate.parse(this.scheduledDate, persistedDateFormatter)
                .toEpochMillis(),
        completed = this.completed,
    )
}

fun Task.toPersistableTask(): PersistableTask {
    val scheduledDate =
        Instant.ofEpochMilli(this.scheduledDateMillis)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()

    return PersistableTask(
        id = this.id,
        description = this.description,
        scheduledDate = scheduledDate.toPersistableDateString(),
        completed = this.completed,
    )
}

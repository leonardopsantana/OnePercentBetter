package com.onepercentbetter.fakes

import com.onepercentbetter.task.api.TaskListResult
import com.onepercentbetter.tasklist.domain.usecases.GetTasksForDateUseCase
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class FakeGetTasksForDateUseCase : GetTasksForDateUseCase {

    private val resultForDateMap: MutableMap<LocalDate, Flow<TaskListResult>> = mutableMapOf()

    fun mockResultForDate(
        date: LocalDate,
        result: Flow<TaskListResult>
    ) {
        resultForDateMap[date] = result
    }

    override fun invoke(date: LocalDate): Flow<TaskListResult> {
        return resultForDateMap[date]!!
    }
}

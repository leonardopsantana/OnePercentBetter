package com.onepercentbetter.fakes

import com.onepercentbetter.core.data.Result
import com.onepercentbetter.tasklist.domain.model.Task
import com.onepercentbetter.tasklist.domain.repository.TaskListRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDate

class FakeTaskListRepository {
    val mock: TaskListRepository = mockk()

    fun mockTasksForDateResult(
        date: LocalDate,
        response: Result<List<Task>>
    ) {
        coEvery {
            mock.fetchTasksForDate(date)
        } returns flowOf(response)
    }
}

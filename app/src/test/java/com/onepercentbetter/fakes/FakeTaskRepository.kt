package com.onepercentbetter.fakes

import com.onepercentbetter.core_data.Result
import com.onepercentbetter.core_model.Task
import com.onepercentbetter.tasklist.domain.repository.TaskRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDate

class FakeTaskRepository {
    val mock: TaskRepository = mockk()

    fun mockTasksForDateResult(
        date: LocalDate,
        response: Result<List<Task>>
    ) {
        coEvery {
            mock.fetchTasksForDate(date, any())
        } returns flowOf(response)
    }
}

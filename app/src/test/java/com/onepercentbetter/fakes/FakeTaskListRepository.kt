package com.onepercentbetter.fakes

import com.onepercentbetter.core.data.Result
import com.onepercentbetter.tasklist.domain.model.Task
import com.onepercentbetter.tasklist.domain.repository.TaskListRepository
import io.mockk.coEvery
import io.mockk.mockk

class FakeTaskListRepository {
    val mock: TaskListRepository = mockk()

    fun mockFetchAllTasks(response: Result<List<Task>>) {
        coEvery {
            mock.fetchAllTasks()
        } returns response
    }
}
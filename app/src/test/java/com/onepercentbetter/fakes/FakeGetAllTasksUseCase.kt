package com.onepercentbetter.fakes

import com.onepercentbetter.core.data.Result
import com.onepercentbetter.tasklist.domain.model.Task
import com.onepercentbetter.tasklist.domain.usecases.GetAllTasksUseCase
import io.mockk.coEvery
import io.mockk.mockk

class FakeGetAllTasksUseCase {

    val mock: GetAllTasksUseCase = mockk()

    fun mockResult(
        response: Result<List<Task>>,
    ) {
        coEvery {
            mock.invoke()
        } returns response
    }
}
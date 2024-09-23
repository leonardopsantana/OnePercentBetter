package com.onepercentbetter.fakes

import com.onepercentbetter.core.model.Task
import com.onepercentbetter.tasklist.domain.usecases.GetAllTasksUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf

class FakeGetAllTasksUseCase {
    val mock: GetAllTasksUseCase = mockk()

    fun mockResult(response: Result<List<Task>>) {
        coEvery {
            mock.invoke()
        } returns flowOf(response)
    }
}

package com.onepercentbetter.fakes

import com.onepercentbetter.addtask.domain.model.AddTaskResult
import com.onepercentbetter.addtask.domain.usecase.AddTaskUseCase
import com.onepercentbetter.tasklist.domain.model.Task
import io.mockk.coEvery
import io.mockk.mockk

class FakeAddTestUseCase {
    val mock: AddTaskUseCase = mockk()

    fun mockResultForTask(
        result: AddTaskResult
    ) {
        coEvery {
            mock.invoke(any())
        } returns result
    }
}

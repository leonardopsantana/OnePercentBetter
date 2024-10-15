package com.onepercentbetter.fakes

import com.onepercentbetter.addtask.domain.model.AddTaskResult
import com.onepercentbetter.addtask.domain.usecase.AddTaskUseCase
import com.onepercentbetter.core.model.Task
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf

class FakeAddTaskUseCase {
    val mock: AddTaskUseCase = mockk(relaxed = true)

    fun mockResultForTask(
        task: Task,
        result: AddTaskResult
    ) {
        coEvery {
            mock.invoke(
                task = any(),
                ignoreTaskLimits = true
            )
        } returns flowOf(result)
    }
}

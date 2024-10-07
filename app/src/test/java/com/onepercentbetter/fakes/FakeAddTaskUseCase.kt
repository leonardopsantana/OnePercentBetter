package com.onepercentbetter.fakes

import com.onepercentbetter.addtask.domain.model.AddTaskResult
import com.onepercentbetter.addtask.domain.usecase.AddTaskUseCase
import com.onepercentbetter.core.model.Task
import io.mockk.coEvery
import io.mockk.mockk

class FakeAddTaskUseCase {
    val mock: AddTaskUseCase = mockk()

//    fun mockResultForTask(result: AddTaskResult) {
//        coEvery {
//            mock.invoke(Task(
//                id = any(),
//                description = any(),
//                scheduledDateMillis = any(),
//                completed = any(),
//            ))
//        } returns result
//    }
}

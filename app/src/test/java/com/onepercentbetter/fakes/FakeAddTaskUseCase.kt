package com.onepercentbetter.fakes

import com.onepercentbetter.addtask.domain.usecase.AddTaskUseCase
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

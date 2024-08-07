package com.onepercentbetter.fakes

import com.google.common.truth.Truth.assertThat
import com.onepercentbetter.core.model.Task
import com.onepercentbetter.tasklist.domain.usecases.RescheduleTaskUseCase
import java.time.LocalDate

class FakeRescheduleTaskUseCase : RescheduleTaskUseCase {
    private val invocations: MutableList<Pair<Task, LocalDate>> = mutableListOf()

    override suspend fun invoke(
        task: Task,
        newDate: LocalDate,
    ) {
        invocations.add(Pair(task, newDate))
    }

    fun verifyInvocation(
        task: Task,
        newDate: LocalDate,
    ) {
        assertThat(invocations).contains(Pair(task, newDate))
    }
}

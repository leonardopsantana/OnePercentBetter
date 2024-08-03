package com.onepercentbetter.fakes

import com.onepercentbetter.core.model.Task
import com.onepercentbetter.tasklist.domain.usecases.RescheduleTaskUseCase
import java.time.LocalDate

class FakeRescheduleTaskUseCase : RescheduleTaskUseCase {
    override suspend fun invoke(task: Task, newDate: LocalDate) {
        //noop
    }


}

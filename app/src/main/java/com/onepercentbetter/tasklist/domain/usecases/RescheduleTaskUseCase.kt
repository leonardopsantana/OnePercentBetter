package com.onepercentbetter.tasklist.domain.usecases

import com.onepercentbetter.core.model.Task
import dagger.Provides
import java.time.LocalDate


/**
 * Consume a task and a new date that we want to reschedule the task for. We will
 * modify that task and save that change in our data layer.
 */
interface RescheduleTaskUseCase {

    suspend operator fun invoke(
        task: Task,
        newDate: LocalDate
    )


}

package com.onepercentbetter.tasklist.ui

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.google.common.truth.Truth.assertThat
import com.onepercentbetter.R
import com.onepercentbetter.tasklist.domain.model.Task
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

class TaskListContentTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val testTask = Task(
        id = "Test ID",
        description = "Text Task",
        scheduledDate = LocalDate.now(),
        completed = false
    )

    @Test
    fun clickPreviousDateButton() {
        var hasClickedPreviousDate = false

        composeTestRule.setContent {
            TaskListContent(
                viewState = TaskListViewState(),
                onRescheduleClicked = {},
                onDoneClicked = {},
                onAddButtonClicked = { },
                onPreviousDateButtonClicked = {
                    hasClickedPreviousDate = true
                },
                onNextDateButtonClicked = {}
            )
        }

        composeTestRule
            .onNodeWithContentDescription(composeTestRule.activity.getString(R.string.view_previous_day_content_description))
            .performClick()

        assertThat(hasClickedPreviousDate).isTrue()
    }

    @Test
    fun clickNextDateButton() {
        var hasClickedNextDate = false

        composeTestRule.setContent {
            TaskListContent(
                viewState = TaskListViewState(),
                onRescheduleClicked = {},
                onDoneClicked = {},
                onAddButtonClicked = { },
                onPreviousDateButtonClicked = {},
                onNextDateButtonClicked = {
                    hasClickedNextDate = true
                }
            )
        }

        composeTestRule
            .onNodeWithContentDescription(composeTestRule.activity.getString(R.string.view_next_day_content_description))
            .performClick()

        assertThat(hasClickedNextDate).isTrue()
    }

    @Test
    fun renderWithNoTasks() {
        val viewState = TaskListViewState(
            showLoading = false,
            incompleteTasks = emptyList(),
            completedTasks = emptyList()
        )

        composeTestRule.setContent {
            TaskListContent(
                viewState = viewState,
                onRescheduleClicked = {},
                onDoneClicked = {},
                onAddButtonClicked = {},
                onPreviousDateButtonClicked = {}) {

            }
        }

        val noTasksScheduledLabel =
            composeTestRule.activity.getString(R.string.no_tasks_scheduled_label)

        composeTestRule
            .onNodeWithText(noTasksScheduledLabel)
            .assertIsDisplayed()
    }

    @Test
    fun renderWithNoIncompleteTasks() {
        val viewState = TaskListViewState(
            showLoading = false,
            incompleteTasks = emptyList(),
            completedTasks = listOf(testTask)
        )

        composeTestRule.setContent {
            TaskListContent(
                viewState = viewState,
                onRescheduleClicked = {},
                onDoneClicked = {},
                onAddButtonClicked = {},
                onPreviousDateButtonClicked = {}) {

            }
        }

        val noIncompleteTasksLabel = composeTestRule.activity.getString(R.string.no_incomplete_tasks_label)

        composeTestRule
            .onNodeWithText(noIncompleteTasksLabel)
            .assertIsDisplayed()

        val expectedTaskTag = "COMPLETE_TASK_${testTask.id}"
        composeTestRule
            .onNodeWithTag(expectedTaskTag)
            .assertIsDisplayed()
    }

    @Test
    fun renderWithNoCompletedTasks() {
        val viewState = TaskListViewState(
            showLoading = false,
            incompleteTasks = listOf(testTask),
            completedTasks = emptyList()
        )

        composeTestRule.setContent {
            TaskListContent(
                viewState = viewState,
                onRescheduleClicked = {},
                onDoneClicked = {},
                onAddButtonClicked = {},
                onPreviousDateButtonClicked = {}) {

            }
        }

        val noCompleteTasksLabel = composeTestRule.activity.getString(R.string.no_complete_tasks_label)

        composeTestRule
            .onNodeWithText(noCompleteTasksLabel)
            .assertIsDisplayed()

        val expectedTaskTag = "INCOMPLETE_TASK_${testTask.id}"
        composeTestRule
            .onNodeWithTag(expectedTaskTag)
            .assertIsDisplayed()
    }
}

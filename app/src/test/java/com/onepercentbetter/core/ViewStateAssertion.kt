package com.onepercentbetter.core

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.StateFlow

/**
 * Launch a coroutine that will observe our [viewModel]'s view state and ensure that we consume
 * all of the supplied [viewStates] in the same order that they are supplied.
 *
 * We should call this near the front of the test, to ensure that every view state we emit
 * can be collected by this.
 */
suspend fun <Robot, ExpectedState, CurrentState> Robot.assertViewStates(
    stateUnderTest: StateFlow<CurrentState>,
    action: Robot.() -> Unit,
    expectedViewStates: List<ExpectedState>,
) {
    stateUnderTest.test {
        action()

        for (state in expectedViewStates) {
            val currentState = awaitItem()
            assertThat(currentState).isEqualTo(state)
        }

        this.cancel()
    }
}

package com.onepercentbetter.fakes

import androidx.navigation.NavOptionsBuilder
import com.google.common.truth.Truth.assertThat
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.Direction

/**
 * This is a concrete implementation of [DestinationsNavigator] that records all requests made
 * so that we can assert the expected navigation occurred.
 */
class FakeDestinationsNavigator : DestinationsNavigator {
    private val navigatedRoutes = mutableListOf<String>()

    override fun clearBackStack(
        route: String,
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun navigate(
        direction: Direction,
        onlyIfResumed: Boolean,
        builder: NavOptionsBuilder.() -> Unit,
    ) {
        navigatedRoutes.add(direction.route)
    }

    override fun navigate(
        route: String,
        onlyIfResumed: Boolean,
        builder: NavOptionsBuilder.() -> Unit,
    ) {
        navigatedRoutes.add(route)
    }

    override fun navigateUp(): Boolean {
        TODO("Not yet implemented")
    }

    override fun popBackStack(): Boolean {
        TODO("Not yet implemented")
    }

    override fun popBackStack(
        route: String,
        inclusive: Boolean,
        saveState: Boolean,
    ): Boolean {
        TODO("Not yet implemented")
    }

    fun verifyNavigatedToDirection(
        expectedDirection: Direction,
    ) {
        val expectedRoute = expectedDirection.route
        assertThat(navigatedRoutes).contains(expectedRoute)
    }
}

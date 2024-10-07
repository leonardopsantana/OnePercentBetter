package com.onepercentbetter.core.ui.components.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.onepercentbetter.R
import com.onepercentbetter.destinations.SettingsScreenDestination
import com.onepercentbetter.destinations.TaskListScreenDestination

/**
 * Represents a tab that can appear on some navigation component.
 */
sealed class NavigationTab(
    val labelTextRes: Int,
    val icon: ImageVector,
    val screenRoute: String,
) {
    object Home : NavigationTab(
        labelTextRes = R.string.home,
        icon = Icons.Default.Home,
        screenRoute = TaskListScreenDestination.route,
    )

    object Settings : NavigationTab(
        labelTextRes = R.string.settings,
        icon = Icons.Default.Settings,
        screenRoute = SettingsScreenDestination.route,
    )
}

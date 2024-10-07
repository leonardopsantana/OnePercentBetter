package com.onepercentbetter.core.ui.components.navigation

/**
 * All forms of navigation within the OPB app share the same
 * core functionality of showing some [NavigationTab] entities and
 * handling their click events.
 */
data class OPBNavigationConfig(
    val tabs: List<NavigationTab>,
    val selectedTab: NavigationTab?,
    val onTabClicked: (NavigationTab) -> Unit,
)

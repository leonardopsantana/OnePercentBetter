package com.onepercentbetter.core.ui.components.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.onepercentbetter.core.ui.components.OPBBottomNavigation
import com.ramcosta.composedestinations.utils.currentDestinationAsState

@Composable
fun OPBNavigationContainer(
    navHostController: NavHostController,
    tabs: List<NavigationTab>,
    navigationType: NavigationType,
    modifier: Modifier = Modifier,
) {
    val currentRoute = navHostController.currentDestinationAsState().value?.route

    val shouldShowNavigation = tabs.any { tab ->
        tab.screenRoute == currentRoute
    }

    val selectedTab = tabs.find { tab ->
        tab.screenRoute == currentRoute
    }

    val onTabClicked = { tab: NavigationTab ->
        if (tab.screenRoute != currentRoute) {
            navHostController.navigate(tab.screenRoute)
        }
    }

    val navigationConfig = OPBNavigationConfig(
        tabs = tabs,
        selectedTab = selectedTab,
        onTabClicked = onTabClicked,
    )

    if (shouldShowNavigation) {
        when (navigationType) {
            NavigationType.BOTTOM_NAVIGATION -> {
                OPBBottomNavigation(
                    navigationConfig,
                    modifier,
                )
            }
            NavigationType.NAVIGATION_RAIL -> {
                OPBNavigationRail(
                    navigationConfig,
                    modifier,
                )
            }
            NavigationType.PERMANENT_NAVIGATION_DRAWER -> {
                OPBNavigationDrawerContent(
                    navigationConfig,
                    modifier,
                )
            }
        }
    }
}

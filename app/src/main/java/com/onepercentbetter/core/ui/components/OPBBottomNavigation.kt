package com.onepercentbetter.core.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.ramcosta.composedestinations.utils.currentDestinationAsState
@Composable
fun OPBBottomNavigation(
    navHostController: NavHostController,
    tabs: List<NavigationTab>,
    modifier: Modifier = Modifier,
) {
    val currentRoute = navHostController.currentDestinationAsState().value?.route

    val shouldShowBottomBar = tabs.any { tab ->
        tab.screenRoute == currentRoute
    }

    AnimatedVisibility(
        visible = shouldShowBottomBar,
        modifier = modifier,
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically(),
    ) {
        NavigationBar {
            tabs.forEach { tab ->
                NavigationBarItem(
                    selected = tab.screenRoute == currentRoute,
                    onClick = {
                        if (tab.screenRoute != currentRoute) {
                            navHostController.navigate(tab.screenRoute)
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = tab.icon,
                            contentDescription = stringResource(id = tab.labelTextRes),
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(id = tab.labelTextRes),
                        )
                    },
                )
            }
        }
    }
}

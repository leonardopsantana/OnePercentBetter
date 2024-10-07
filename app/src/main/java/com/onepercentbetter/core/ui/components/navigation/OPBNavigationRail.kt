package com.onepercentbetter.core.ui.components.navigation

import android.content.res.Configuration
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.onepercentbetter.ExcludeFromJacocoGeneratedReport
import com.onepercentbetter.core.ui.theme.OPBTheme

@Composable
fun OPBNavigationRail(
    navigationConfig: OPBNavigationConfig,
    modifier: Modifier = Modifier,
) {
    NavigationRail(
        modifier = modifier,
    ) {
        navigationConfig.tabs.forEach { tab ->
            NavigationRailItem(
                selected = tab == navigationConfig.selectedTab,
                onClick = {
                    navigationConfig.onTabClicked.invoke(tab)
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

@Preview(
    name = "Night Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Night Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
@ExcludeFromJacocoGeneratedReport
private fun TOANavigationRailPreview() {
    OPBTheme {
        OPBNavigationRail(
            navigationConfig = OPBNavigationConfig(
                tabs = listOf(NavigationTab.Home, NavigationTab.Settings),
                selectedTab = NavigationTab.Home,
                onTabClicked = {},
            ),
        )
    }
}

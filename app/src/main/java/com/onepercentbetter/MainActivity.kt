@file:OptIn(ExperimentalMaterialNavigationApi::class)

package com.onepercentbetter

import android.R
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.onepercentbetter.core.ui.components.navigation.NavigationTab
import com.onepercentbetter.core.ui.components.navigation.NavigationType
import com.onepercentbetter.core.ui.components.navigation.OPBNavigationContainer
import com.onepercentbetter.core.ui.theme.OPBTheme
import com.onepercentbetter.destinations.LoginScreenDestination
import com.onepercentbetter.destinations.TaskListScreenDestination
import com.onepercentbetter.session.SessionState
import com.onepercentbetter.session.SessionViewModel
import com.onepercentbetter.tasklist.ui.TaskListScreen
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import com.ramcosta.composedestinations.spec.Route
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    private val sessionViewModel: SessionViewModel by viewModels()

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(
        savedInstanceState: Bundle?,
    ) {
        super.onCreate(savedInstanceState)

        keepSplashScreenVisibleWhileInitializing()

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val windowWidthSizeClass = calculateWindowSizeClass(activity = this).widthSizeClass

            OPBTheme {
                ConfigureSystemBars()

                Surface(
                    color = MaterialTheme.colorScheme.background,
                ) {
                    val sessionState = sessionViewModel.sessionState.collectAsState()

                    val startRoute: Route? = when (sessionState.value) {
                        SessionState.UNINITIALIZED -> null
                        SessionState.LOGGED_IN -> TaskListScreenDestination
                        SessionState.LOGGED_OUT -> LoginScreenDestination
                    }

                    if (startRoute != null) {
                        OPBNavHost(startRoute, windowWidthSizeClass)
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalMaterial3WindowSizeClassApi::class)
    @Composable
    private fun OPBNavHost(
        startRoute: Route,
        windowWidthSizeClass: WindowWidthSizeClass,
    ) {
        val navigationEngine = rememberAnimatedNavHostEngine(
            rootDefaultAnimations = RootNavGraphDefaultAnimations(
                enterTransition = {
                    slideInHorizontally()
                },
                exitTransition = {
                    fadeOut()
                },
            ),
        )

        val navController = navigationEngine.rememberNavController()

        val windowSizeClass = calculateWindowSizeClass(activity = this)

        val navigationType = getNavigationType(windowSizeClass.widthSizeClass)

        val navigationTabs = listOf(
            NavigationTab.Home,
            NavigationTab.Settings,
        )

        OPBAppScaffold(
            navigationType = navigationType,
            navigationContent = {
                OPBNavigationContainer(
                    navHostController = navController,
                    tabs = navigationTabs,
                    navigationType = navigationType,
                )
            },
            appContent = {
                DestinationsNavHost(
                    startRoute = startRoute,
                    navGraph = NavGraphs.root,
                    engine = navigationEngine,
                    navController = navController,
                    manualComposableCallsBuilder = {
                        composable(TaskListScreenDestination) {
                            TaskListScreen(
                                navigator = destinationsNavigator,
                                windowWidthSizeClass = windowWidthSizeClass,
                            )
                        }
                    },
                )
            },
        )
    }

    private fun getNavigationType(
        widthSizeClass: WindowWidthSizeClass,
    ): NavigationType =
        when (widthSizeClass) {
            WindowWidthSizeClass.Expanded -> NavigationType.PERMANENT_NAVIGATION_DRAWER
            WindowWidthSizeClass.Medium -> NavigationType.NAVIGATION_RAIL
            else -> NavigationType.BOTTOM_NAVIGATION
        }

    private fun keepSplashScreenVisibleWhileInitializing() {
        val content: View = findViewById(R.id.content)

        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    val currentSessionState = sessionViewModel.sessionState.value
                    val isInitializing = currentSessionState == SessionState.UNINITIALIZED

                    return if (isInitializing) {
                        // We don't have data yet, don't draw anything.
                        false
                    } else {
                        // we're ready, start drawing
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    }
                }
            },
        )
    }

    @Composable
    private fun ConfigureSystemBars() {
        val systemUiController = rememberSystemUiController()
        val useDarkIcons = !isSystemInDarkTheme()

        SideEffect {
            // Update all of the system bar colors to be transparent, and use
            // dark icons if we're in light theme
            systemUiController.setSystemBarsColor(
                color = Color.Transparent,
                darkIcons = useDarkIcons,
            )
        }
    }
}

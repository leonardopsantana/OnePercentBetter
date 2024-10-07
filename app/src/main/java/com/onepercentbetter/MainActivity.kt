@file:OptIn(ExperimentalMaterialNavigationApi::class)

package com.onepercentbetter

import android.os.Bundle
import android.R
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.onepercentbetter.core.ui.components.NavigationTab
import com.onepercentbetter.core.ui.components.OPBBottomNavigation
import com.onepercentbetter.core.ui.components.WindowSize
import com.onepercentbetter.core.ui.components.rememberWindowSizeClass
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        keepSplashScreenVisibleWhileInitializing()

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val windowSize = rememberWindowSizeClass()

            OPBTheme {
                ConfigureSystemBars()

                ProvideWindowInsets {
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
                            OPBNavHost(startRoute, windowSize)
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun OPBNavHost(
        startRoute: Route,
        windowSize: WindowSize,
    ) {
        val navigationEngine = rememberAnimatedNavHostEngine(
            rootDefaultAnimations = RootNavGraphDefaultAnimations(
                enterTransition = {
                    slideInHorizontally()
                },
                exitTransition = {
                    fadeOut()
                },
            )
        )

        val navController = navigationEngine.rememberNavController()

        Column {
            DestinationsNavHost(
                startRoute = startRoute,
                navGraph = NavGraphs.root,
                engine = navigationEngine,
                navController = navController,
                manualComposableCallsBuilder = {
                    composable(TaskListScreenDestination) {
                        TaskListScreen(
                            navigator = destinationsNavigator,
                            windowSize = windowSize,
                        )
                    }
                },
                modifier = Modifier
                    .weight(1F),
            )

            OPBBottomNavigation(
                navHostController = navController,
                tabs = listOf(
                    NavigationTab.Home,
                    NavigationTab.Settings,
                ),
            )
        }
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
            }
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

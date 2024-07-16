package com.onepercentbetter


import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.onepercentbetter.core.ui.theme.OPBTheme
import com.onepercentbetter.destinations.TaskListScreenDestination
import com.onepercentbetter.tasklist.ui.TaskListScreen
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    @OptIn(ExperimentalMaterialNavigationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            OPBTheme {
                ConfigureSystemBars()

                ProvideWindowInsets {
                    Surface(
                        color = MaterialTheme.colorScheme.background,
                    ) {
                        DestinationsNavHost(
                            navGraph = NavGraphs.root,
                            engine = rememberAnimatedNavHostEngine(
                                rootDefaultAnimations = RootNavGraphDefaultAnimations(
                                    enterTransition = {
                                        slideInHorizontally()
                                    },
                                    exitTransition = {
                                        fadeOut()
                                    },
                                ),
                            ),
                            manualComposableCallsBuilder = {
                                composable(TaskListScreenDestination) {
                                    TaskListScreen(navigator = destinationsNavigator)
                                }
                            }
                        )
                    }
                }
            }
        }
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

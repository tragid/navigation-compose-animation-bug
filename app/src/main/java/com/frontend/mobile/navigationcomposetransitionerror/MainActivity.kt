package com.frontend.mobile.navigationcomposetransitionerror

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.frontend.mobile.navigationcomposetransitionerror.views.HomeScreen
import com.frontend.mobile.navigationcomposetransitionerror.views.Page2Screen
import com.frontend.mobile.navigationcomposetransitionerror.views.Page3Screen
import kotlin.reflect.KClass

const val defaultAnimationEnterDuration = 200

fun defaultFadeEnterTransition(): EnterTransition {
    return fadeIn(
        animationSpec = tween(defaultAnimationEnterDuration)
    )
}


fun defaultFadeExitTransition(): ExitTransition {
    return fadeOut(
        animationSpec = tween(defaultAnimationEnterDuration * 2)
    )
}

fun NavDestination.hasActiveRoute(route: KClass<*>): Boolean {
    return hierarchy.any {
        it.hasRoute(route)
    }
}

fun NavController.navigateFeature(route: Any) {
    navigate(route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }
}

@Composable
fun BottomTabItem(
    modifier: Modifier = Modifier,
    title: String,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .height(60.dp)
            .background(androidx.compose.ui.graphics.Color.Cyan)
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center,
    ) {
        Text(text = title)
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT, Color.TRANSPARENT
            )
        )
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            Scaffold(
                contentWindowInsets = WindowInsets.navigationBars,
                containerColor = androidx.compose.ui.graphics.Color.White,
                contentColor = androidx.compose.ui.graphics.Color.Black,
                modifier = Modifier.background(androidx.compose.ui.graphics.Color.White),
                bottomBar = {
                    Row(
                        modifier = Modifier.background(androidx.compose.ui.graphics.Color.Red),
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                    ) {
                        BottomTabItem(
                            title = "Home", modifier = Modifier.weight(1f),
                            onClick = {
                                navController.navigateFeature(HomeBaseRoute)
                            }
                        )
                        BottomTabItem(
                            title = "Page2", modifier = Modifier.weight(1f),
                            onClick = {
                                navController.navigateFeature(Page2BaseRoute)
                            }
                        )
                        BottomTabItem(
                            title = "Page3", modifier = Modifier.weight(1f),
                            onClick = {
                                navController.navigate(Page3Route)
                            }
                        )
                    }
                }
            ) { padding ->
                Box(modifier = Modifier.fillMaxSize().padding(padding)) {
                    NavHost(
                        navController = navController,
                        startDestination = HomeBaseRoute,
                        enterTransition = {
                            EnterTransition.None
                        },
                        exitTransition = {
                            ExitTransition.None
                        },
                    ) {
                        navigation<HomeBaseRoute>(
                            startDestination = HomeRoute,
                        ) {
                            composable<HomeRoute>(
                                enterTransition = {
                                    defaultFadeEnterTransition()
                                },
                                exitTransition = {
                                    defaultFadeExitTransition()
                                },
                                popEnterTransition = {
                                    defaultFadeEnterTransition()
                                },
                                popExitTransition = {
                                    defaultFadeExitTransition()
                                },
                            ) {
                                HomeScreen()
                            }
                        }

                        navigation<Page2BaseRoute>(
                            startDestination = Page2Route
                        ) {
                            composable<Page2Route>(
                                enterTransition = {
                                    defaultFadeEnterTransition()
                                },
                                exitTransition = {
                                    defaultFadeExitTransition()
                                },
                                popEnterTransition = {
                                    defaultFadeEnterTransition()
                                },
                                popExitTransition = {
                                    defaultFadeExitTransition()
                                },
                            ) {
                                Page2Screen()
                            }
                        }

                        navigation<Page3BaseRoute>(
                            startDestination = Page3Route
                        ) {
                            composable<Page3Route>(
                                enterTransition = {
                                    if (initialState.destination.hasActiveRoute(Page3Route::class)) {
                                        slideIntoContainer(
                                            AnimatedContentTransitionScope.SlideDirection.Left,
                                            animationSpec = tween(300)
                                        )
                                    } else {
                                        slideIntoContainer(
                                            AnimatedContentTransitionScope.SlideDirection.Up,
                                            animationSpec = tween(300)
                                        )
                                    }
                                },
                                exitTransition = {
                                    if (targetState.destination.hasActiveRoute(Page3Route::class)) {
                                        slideOutOfContainer(
                                            AnimatedContentTransitionScope.SlideDirection.Left,
                                            animationSpec = tween(300)
                                        )
                                    } else {
                                        slideOutOfContainer(
                                            AnimatedContentTransitionScope.SlideDirection.Down,
                                            animationSpec = tween(300)
                                        )
                                    }
                                },
                                popEnterTransition = {
                                    if (initialState.destination.hasActiveRoute(Page3Route::class)) {
                                        slideIntoContainer(
                                            AnimatedContentTransitionScope.SlideDirection.Right,
                                            animationSpec = tween(300)
                                        )
                                    } else {
                                        slideIntoContainer(
                                            AnimatedContentTransitionScope.SlideDirection.Up,
                                            animationSpec = tween(300)
                                        )
                                    }
                                },
                                popExitTransition = {
                                    if (targetState.destination.hasActiveRoute(Page3Route::class)) {
                                        slideOutOfContainer(
                                            AnimatedContentTransitionScope.SlideDirection.Right,
                                            animationSpec = tween(300)
                                        )
                                    } else {
                                        slideOutOfContainer(
                                            AnimatedContentTransitionScope.SlideDirection.Down,
                                            animationSpec = tween(300)
                                        )
                                    }
                                },
                            ) {
                                Page3Screen()
                            }
                        }
                    }
                }
            }
        }
    }
}
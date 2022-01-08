package com.nipunapps.hncc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.nipunapps.hncc.core.noRippleClickable
import com.nipunapps.hncc.feature_hncc.presentation.screen.AboutScreen
import com.nipunapps.hncc.feature_hncc.presentation.screen.MainScreen
import com.nipunapps.hncc.feature_hncc.presentation.screen.NavigationScreen
import com.nipunapps.hncc.feature_hncc.presentation.screen.TeamScreen
import com.nipunapps.hncc.feature_hncc.presentation.viewmodels.MainViewModel
import com.nipunapps.hncc.ui.Screen
import com.nipunapps.hncc.ui.theme.HamPadding
import com.nipunapps.hncc.ui.theme.HnCCTheme
import com.nipunapps.hncc.ui.theme.PaddingStatusBar
import com.nipunapps.hncc.ui.theme.SmallPadding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HnCCTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val viewModel: MainViewModel = hiltViewModel()
                    val navController = rememberAnimatedNavController()
                    val scaffoldState = rememberScaffoldState()
                    val coroutine = rememberCoroutineScope()
                    Scaffold(
                        scaffoldState = scaffoldState,
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            TopBar(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(SmallPadding)
                            ) {
                                coroutine.launch {
                                    scaffoldState.drawerState.open()
                                }
                            }
                        },
                        drawerContent = {
                            NavigationScreen(
                                modifier = Modifier.fillMaxSize()
                            ) { route ->
                                navController.navigate(route = route) {
                                    popUpTo(Screen.HomeScreen.route)
                                    launchSingleTop = true
                                }
                                coroutine.launch {
                                    scaffoldState.drawerState.close()
                                }
                            }
                        }
                    ) {
                        AnimatedNavHost(
                            navController = navController,
                            startDestination = Screen.HomeScreen.route
                        ) {
                            composable(
                                route = Screen.HomeScreen.route
                            ) {
                                MainScreen(
                                    navController = navController,
                                    viewModel = viewModel
                                )
                            }

                            composable(
                                route = Screen.AboutScreen.route
                            ) {
                                AboutScreen()
                            }
                            composable(
                                route = Screen.TeamScreen.route
                            ) {
                                TeamScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    onIconClick: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        HamBurger(
            modifier = Modifier
                .width(PaddingStatusBar)
                .aspectRatio(1.2f)
        ) {
            onIconClick()
        }

        Image(
            painter = painterResource(
                id = R.drawable.logo
            ),
            contentDescription = "Logo",
            modifier = Modifier.size(PaddingStatusBar)
        )
    }
}

@Composable
fun HamBurger(
    modifier: Modifier = Modifier,
    onIconClick: () -> Unit
) {
    Canvas(
        modifier = modifier
            .padding(HamPadding)
            .noRippleClickable {
                onIconClick()
            }
    ) {
        val width = this.size.width
        val height = this.size.height
        val size = height / 5.0f
        drawRect(
            color = Color.White,
            topLeft = Offset(0f, 0f),
            size = Size(
                width = width,
                height = size
            )
        )

        drawRect(
            color = Color.White,
            topLeft = Offset(0f, 2 * size),
            size = Size(
                width = width,
                height = size
            )
        )

        drawRect(
            color = Color.White,
            topLeft = Offset(0f, 4 * size),
            size = Size(
                width = width,
                height = height / 5.0f
            )
        )
    }
}
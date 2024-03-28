package com.application.travel_app.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.application.travel_app.data.viewmodel.TravelsViewModel
import com.application.travel_app.navigation.Screen
import com.application.travel_app.ui.theme.TravelAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            TravelAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val travelsViewModel = TravelsViewModel(application)
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.HomeScreen.route
                    ) {
                        composable(route = Screen.HomeScreen.route) {
                            HomeScreen(navController = navController)
                        }
                        composable(route = Screen.AddTravelScreen.route) {
                            AddTravelScreen(travelsViewModel = travelsViewModel)
                        }
                        composable(route = Screen.AllTravelsScreen.route) {
                            AllTravelsScreen(
                                navController = navController,
                                travelsViewModel = travelsViewModel
                            )
                        }
                        composable(
                            route = Screen.TravelDetailsScreen.route + "/{travel_id}",
                            arguments = listOf(
                                navArgument("travel_id") {
                                    type = NavType.IntType
                                    defaultValue = -1
                                    nullable = false
                                }
                            )
                        ) {
                            val id = it.arguments?.getInt("travel_id") ?: -1
                            TravelDetailScreen(id, travelsViewModel = travelsViewModel, navController)
                        }
                    }
                }
            }
        }
    }
}
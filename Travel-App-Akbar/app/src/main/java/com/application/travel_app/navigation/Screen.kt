package com.application.travel_app.navigation

sealed class Screen(val route: String) {
    object HomeScreen : Screen("main_screen")
    object AddTravelScreen : Screen("add_a_travel_place")
    object TravelDetailsScreen : Screen("travel_place_details")
    object AllTravelsScreen : Screen("all_travel_place")
}

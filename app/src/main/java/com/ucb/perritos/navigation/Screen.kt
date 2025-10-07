package com.ucb.perritos.navigation

sealed class Screen(val route: String) {
    // EXAMPLES
    object Login: Screen("login")
//    object Github: Screen("github")
//    object Profile: Screen("profile")
//
//    object CardExamples: Screen("card")
//    object Dollar: Screen("dollar")
//    object PopularMovies: Screen("popularMovies")
}
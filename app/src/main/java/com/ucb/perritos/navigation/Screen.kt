package com.ucb.perritos.navigation

sealed class Screen(val route: String) {
    // EXAMPLES
    object Login: Screen("login")
    object Bienvenida: Screen("bienvenida")
    object RegistroPerro: Screen("registroPerro")
    object RegistroUsuario: Screen("registroUsuario")

    object Menu: Screen("menu")
//    object Profile: Screen("profile")
//
//    object CardExamples: Screen("card")
//    object Dollar: Screen("dollar")
//    object PopularMovies: Screen("popularMovies")
}
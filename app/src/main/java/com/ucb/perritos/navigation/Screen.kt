package com.ucb.perritos.navigation

sealed class Screen(val route: String) {
    // EXAMPLES
    object Login: Screen("login")
    object Bienvenida: Screen("bienvenida")
    object RegistroPerro: Screen("registroPerro")
    object RegistroUsuario: Screen("registroUsuario")

    object Menu: Screen("menu")

    object PerfilPerro: Screen("perfilPerro")
    object PerfilDueno: Screen("perfilDueno")
}
package com.ucb.perritos.navigation

sealed class Screen(val route: String) {
    // EXAMPLES
    object Login: Screen("login")
    object Bienvenida: Screen("bienvenida")
    object RegistroPerro: Screen("registroPerro")
    object RegistroUsuario: Screen("registroUsuario")

    object Menu: Screen("menu")

    //object PerfilPerro: Screen("perfilPerro")
    object PerfilPerro: Screen("perfilPerro/{perroId}") {
        fun createRoute(perroId: Long) = "perfilPerro/$perroId"
    }
    object PaginaDeCarga : Screen("pagina_de_carga")
    object BuscarPerro : Screen("buscar_perro")
    object MisPerros: Screen("mis_perros")
    object PerfilUsuario: Screen("perfil_usuario")


}
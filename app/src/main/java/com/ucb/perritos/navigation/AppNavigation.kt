package com.ucb.perritos.navigation



import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ucb.perritos.features.bienvenida.presentation.BienvenidaScreen
import com.ucb.perritos.features.buscarMascota.presentation.BuscarMascotaScreen
import com.ucb.perritos.features.login.presentation.LoginScreen
import com.ucb.perritos.features.menu.presentation.MenuScreen
import com.ucb.perritos.features.perfilPerro.presentation.PerfilPerroScreen
import com.ucb.perritos.features.registroMascota.presentation.RegistroPerroScreen
import com.ucb.perritos.features.registroUsuario.presentation.RegistroUsuarioScreen
import com.ucb.perritos.ui.paginaDeCarga.PaginaDeCargaScreen

@Composable
fun AppNavigation(navigationViewModel: NavigationViewModel) {
    val navController: NavHostController = rememberNavController()

    // Manejar navegación desde el ViewModel
    LaunchedEffect(Unit) {
        navigationViewModel.navigationCommand.collect { command ->
            when (command) {
                is NavigationViewModel.NavigationCommand.NavigateTo -> {
                    navController.navigate(command.route) {
                        // Configuración del back stack según sea necesario
                        when (command.options) {
                            NavigationOptions.CLEAR_BACK_STACK -> {
                                popUpTo(0) // Limpiar todo el back stack
                            }
                            NavigationOptions.REPLACE_HOME -> {
                                popUpTo(Screen.Bienvenida.route) { inclusive = true }
                            }
                            else -> {
                                // Navegación normal
                            }
                        }
                    }
                }
                is NavigationViewModel.NavigationCommand.PopBackStack -> {
                    navController.popBackStack()
                }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Bienvenida.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                irRegistroCuenta = {
                    navController.navigate(Screen.RegistroUsuario.route)
                },
                irRegistroMascota = {
                    navController.navigate(Screen.RegistroPerro.route)
                },
                irMapa = {
                    navController.navigate(Screen.BuscarPerro.route)
                }
            )
        }
        composable(Screen.Bienvenida.route) {
            BienvenidaScreen(
                navigateLogin = {
                    navController.navigate(Screen.Login.route)
                },
                navigateRegistroUsuario = {
                    navController.navigate(Screen.RegistroUsuario.route)
                }
            )
        }
        composable(Screen.RegistroPerro.route) {
            RegistroPerroScreen(
                irMapa = {
                    navController.navigate(Screen.BuscarPerro.route)
                }
            )
        }
        composable(Screen.RegistroUsuario.route) {
            RegistroUsuarioScreen(
                onVolverClick = {
                    navController.navigate(Screen.Bienvenida.route)
                },
                irLogin = {
                    navController.navigate(Screen.Login.route)
                }
            )
        }
        composable(Screen.Menu.route) {
            MenuScreen()
        }
        composable(Screen.PerfilPerro.route) {
            PerfilPerroScreen()
        }
        composable(Screen.PaginaDeCarga.route) {
            PaginaDeCargaScreen(
                irBienvenida = {
                    navController.navigate(Screen.Bienvenida.route) {
                        // esto hace que al apretar "back" después,
                        // NO vuelva a la pantalla de carga
                        popUpTo(Screen.PaginaDeCarga.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.BuscarPerro.route) {
            BuscarMascotaScreen()
        }
    }


}
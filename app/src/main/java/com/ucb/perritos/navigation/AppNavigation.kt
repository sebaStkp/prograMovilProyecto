package com.ucb.perritos.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ucb.perritos.features.bienvenida.presentation.BienvenidaScreen
import com.ucb.perritos.features.buscarMascota.presentation.BuscarMascotaScreen
import com.ucb.perritos.features.login.presentation.LoginScreen


import com.ucb.perritos.features.menu.presentation.MenuScreen

import com.ucb.perritos.features.perfilPerro.presentation.PerfilPerroScreen
import com.ucb.perritos.features.perfilUsuario.presentation.PerfilUsuarioScreen
import com.ucb.perritos.features.perrosRegistrados.presentation.PerrosRegistradosScreen
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
                        when (command.options) {
                            NavigationOptions.CLEAR_BACK_STACK -> {
                                popUpTo(0)
                            }
                            NavigationOptions.REPLACE_HOME -> {
                                // Ajusta esto según cuál sea tu "Home" real (ej. Bienvenida o Menu)
                                popUpTo(Screen.Bienvenida.route) { inclusive = true }
                            }
                            else -> { /* Navegación normal */ }
                        }
                    }
                }
                is NavigationViewModel.NavigationCommand.PopBackStack -> {
                    navController.popBackStack()
                }
            }
        }
    }

    // 1. DEFINIMOS DÓNDE SE VE EL MENÚ
    val bottomBarRoutes = listOf(
        Screen.Menu.route,
        Screen.PerfilPerro.route,
        Screen.MisPerros.route,
        Screen.BuscarPerro.route,
        Screen.PerfilUsuario.route
        // Agrega aquí 'citas' o 'seguro' si creas esas pantallas
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val showBottomBar = currentRoute in bottomBarRoutes

    // 2. USAMOS UN BOX PARA SUPERPONER EL MENÚ
    Box(modifier = Modifier.fillMaxSize()) {

        NavHost(
            navController = navController,
            startDestination = Screen.Bienvenida.route,
            modifier = Modifier.fillMaxSize()
        ) {
            composable(Screen.Login.route) {
                LoginScreen(
                    irRegistroCuenta = { navController.navigate(Screen.RegistroUsuario.route) },
                    irRegistroMascota = { navController.navigate(Screen.RegistroPerro.route) },
                    irMapa = { navController.navigate(Screen.BuscarPerro.route) },
                    irMisPerros = { navController.navigate(Screen.MisPerros.route) }
                )
            }
            composable(Screen.Bienvenida.route) {
                BienvenidaScreen(
                    navigateLogin = { navController.navigate(Screen.Login.route) },
                    navigateRegistroUsuario = { navController.navigate(Screen.RegistroUsuario.route) }
                )
            }
            composable(Screen.RegistroPerro.route) {
                RegistroPerroScreen(
                    irMapa = { navController.navigate(Screen.BuscarPerro.route) }
                )
            }
            composable(Screen.RegistroUsuario.route) {
                RegistroUsuarioScreen(
                    onVolverClick = { navController.navigate(Screen.Bienvenida.route) },
                    irLogin = { navController.navigate(Screen.Login.route) }
                )
            }

            // Si MenuScreen es solo una pantalla vacía con el menú, puedes dejarlo así,
            // o usarlo como landing page.
            composable(Screen.Menu.route) {
                // Contenido de la pantalla Home/Menu si tienes alguno
                Box(modifier = Modifier.fillMaxSize()) // Placeholder
            }

            composable(Screen.PerfilPerro.route) {
                PerfilPerroScreen()
            }

            composable(Screen.PaginaDeCarga.route) {
                PaginaDeCargaScreen(
                    irBienvenida = {
                        navController.navigate(Screen.Bienvenida.route) {
                            popUpTo(Screen.PaginaDeCarga.route) { inclusive = true }
                        }
                    }
                )
            }

            composable(Screen.BuscarPerro.route) {
                BuscarMascotaScreen()
            }

            composable(Screen.MisPerros.route) {
                PerrosRegistradosScreen(
                    irRegistroPerro = { navController.navigate(Screen.RegistroPerro.route) }
                )
            }
            composable(Screen.PerfilUsuario.route) {
                PerfilUsuarioScreen(

                )
            }
        }

        // 3. EL MENÚ FLOTANTE AL FONDO
        if (showBottomBar) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
            ) {
                MenuScreen(
                    navController = navController,
                    navigationViewModel = navigationViewModel
                )
            }
        }
    }
}
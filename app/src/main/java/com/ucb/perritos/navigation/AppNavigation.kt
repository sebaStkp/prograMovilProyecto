package com.ucb.perritos.navigation



import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import android.content.Context
import com.ucb.perritos.features.bienvenida.presentation.BienvenidaScreen
import com.ucb.perritos.features.login.presentation.LoginScreen
import com.ucb.perritos.features.menu.presentation.HomeScreen
import com.ucb.perritos.features.perfilPerro.presentation.PerfilPerroScreen
import com.ucb.perritos.features.registroMascota.presentation.RegistroPerroScreen
import com.ucb.perritos.features.registroUsuario.presentation.RegistroUsuarioScreen
import com.ucb.perritos.features.perfildueno.presentation.PerfilDuenoScreen
import com.ucb.perritos.features.perfildueno.di.PerfilDuenoModule
import com.ucb.perritos.features.perfildueno.presentation.PerfilDuenoViewModel
import kotlinx.coroutines.launch

@Composable
fun AppNavigation(navigationViewModel: NavigationViewModel, navControllerParam: NavHostController? = null) {
    // Use provided NavHostController (from MainActivity) or create one locally
    val navController: NavHostController = navControllerParam ?: rememberNavController()
    val context: Context = LocalContext.current

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
        // Inicio en Bienvenida (mostrar botones Registrar / Iniciar sesión)
        startDestination = Screen.Bienvenida.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                irRegistroCuenta = {
                    navController.navigate(Screen.RegistroUsuario.route)
                },
                onLoginSuccess = {
                    navController.navigate(Screen.Menu.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
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
            RegistroPerroScreen()
        }
        composable(Screen.RegistroUsuario.route) {
            // Pasamos onRegistered para guardar el perfil del dueño y navegar a Login
            val repo = PerfilDuenoModule.provideRepository(context)
            val scope = rememberCoroutineScope()
            RegistroUsuarioScreen(
                onVolverClick = {
                    navController.navigate(Screen.Bienvenida.route)
                },
                onRegistered = { usuario ->
                    // Construir PerfilDuenoModel a partir de UsuarioModel
                    val perfil = com.ucb.perritos.features.perfildueno.domain.model.PerfilDuenoModel(
                        id = usuario.email ?: "owner",
                        nombre = usuario.nombreDueño ?: "",
                        email = usuario.email ?: "",
                        telefono = "" // no recogemos teléfono en registro de usuario
                    )
                    scope.launch {
                        try {
                            repo.actualizarPerfil(perfil)
                        } catch (_: Exception) {
                            // ignorar
                        }
                        // Después de registrarse, ir a Login para iniciar sesión
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.RegistroUsuario.route) { inclusive = true }
                        }
                    }
                }
            )
        }
        composable(Screen.Menu.route) {
            HomeScreen(navController = navController)
        }
        composable(Screen.PerfilPerro.route) {
            PerfilPerroScreen()
        }
        composable(Screen.PerfilDueno.route) {
            // Conectar el ViewModel usando el module factory (usa InMemory si no hay context disponible)
            val vm = remember { PerfilDuenoViewModel(PerfilDuenoModule.provideObtenerPerfilUseCase(context), PerfilDuenoModule.provideEliminarCuentaUseCase(context)) }
            PerfilDuenoScreen(vm, onAccountDeleted = {
                // al eliminar cuenta, ir a Bienvenida y limpiar backstack del menu
                navController.navigate(Screen.Bienvenida.route) {
                    popUpTo(Screen.Menu.route) { inclusive = true }
                }
            })
        }
    }


}
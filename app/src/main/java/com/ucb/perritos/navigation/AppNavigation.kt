package com.ucb.perritos.navigation



import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ucb.perritos.features.login.presentation.LoginScreen


@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen()
        }
    }
}



//@Composable
//fun AppNavigation(navigationViewModel: NavigationViewModel) {
//    val navController: NavHostController = rememberNavController()
//
//    // Manejar navegación desde el ViewModel
//    LaunchedEffect(Unit) {
//        navigationViewModel.navigationCommand.collect { command ->
//            when (command) {
//                is NavigationViewModel.NavigationCommand.NavigateTo -> {
//                    navController.navigate(command.route) {
//                        // Configuración del back stack según sea necesario
//                        when (command.options) {
//                            NavigationOptions.CLEAR_BACK_STACK -> {
//                                popUpTo(0) // Limpiar todo el back stack
//                            }
//                            NavigationOptions.REPLACE_HOME -> {
//                                popUpTo(Screen.Dollar.route) { inclusive = true }
//                            }
//                            else -> {
//                                // Navegación normal
//                            }
//                        }
//                    }
//                }
//                is NavigationViewModel.NavigationCommand.PopBackStack -> {
//                    navController.popBackStack()
//                }
//            }
//        }
//    }
//
//    NavHost(
//        navController = navController,
//        startDestination = Screen.PopularMovies.route
//    ) {
//        composable(Screen.Github.route) {
//            GithubScreen(modifier = Modifier)
//        }
//        composable(Screen.Home.route) {
//
//        }
//        composable(Screen.Profile.route) {
//            ProfileScreen()
//        }
//
//        composable(Screen.CardExamples.route) { CardScreen() }
//
//        composable(Screen.Dollar.route) {
//            DollarScreen()
//        }
//
//        composable(Screen.PopularMovies.route) { PopularMoviesScreen() }
//    }
//
//
//}
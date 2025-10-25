package com.ucb.perritos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ucb.perritos.navigation.AppNavigation
import com.ucb.perritos.navigation.NavigationViewModel
import com.ucb.perritos.features.menu.presentation.MenuScreen


class MainActivity : ComponentActivity() {
    private val navigationViewModel: NavigationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController: NavHostController = rememberNavController()

            // Obtener ruta actual para mostrar/ocultar el bottom bar
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            val showBottomBar = currentRoute in listOf(
                "${"menu"}", // Screen.Menu.route
                "${"perfilPerro"}",
                "${"perfilDueno"}"
            )

            Scaffold(
                bottomBar = {
                    if (showBottomBar) {
                        MenuScreen(
                            onShieldClick = { /* TODO */ },
                            onCalendarClick = { /* TODO */ },
                            onPawClick = { navController.navigate("perfilPerro") },
                            onProfileClick = { navController.navigate("perfilDueno") }
                        )
                    }
                },
                content = { paddingValues ->
                    Box(
                        modifier = Modifier
                            .padding(paddingValues)
                            .navigationBarsPadding()
                    )
                    {
                        AppNavigation(navigationViewModel, navController)
                    }
                },
                contentWindowInsets = WindowInsets(bottom = 0.dp)
            )
        }

    }
}




//class MainActivity : ComponentActivity() {
//    private val navigationViewModel: NavigationViewModel by viewModels()
//    private var currentIntent: Intent? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        currentIntent = intent
//
//        enableEdgeToEdge()
//        setContent {
//            LaunchedEffect(Unit) {
//                Log.d("MainActivity", "onCreate - Procesando intent inicial")
//                navigationViewModel.handleDeepLink(currentIntent)
//            }
//
//            LaunchedEffect(Unit) {
//                snapshotFlow { currentIntent }
//                    .distinctUntilChanged()
//                    .collect { intent ->
//                        Log.d("MainActivity", "Nuevo intent recibido: ${intent?.action}")
//                        navigationViewModel.handleDeepLink(intent)
//                    }
//            }
//
//            AppNavigation(navigationViewModel)
//        }
//    }
//
//    override fun onNewIntent(intent: Intent) {
//        super.onNewIntent(intent)
//        Log.d("MainActivity", "onNewIntent llamado")
//
//        this.intent = intent
//        currentIntent = intent
//
//        navigationViewModel.handleDeepLink(intent)
//    }
//}

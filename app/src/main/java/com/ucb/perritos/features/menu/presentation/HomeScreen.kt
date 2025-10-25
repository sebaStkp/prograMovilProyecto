package com.ucb.perritos.features.menu.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    // El contenido principal se gestiona aquí. La barra inferior está en el Scaffold de MainActivity
    Box(modifier = androidx.compose.ui.Modifier.fillMaxSize()) {
        // Muestra contenido predeterminado; las acciones de la barra inferior navegan usando navController
        Text(text = "Inicio / Home")
    }
}

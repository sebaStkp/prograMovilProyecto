package com.ucb.perritos.features.registro.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun RegistroScreen(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        RegistroForm(
            onRegistrarClick = {
                // Por ahora no hace nada, solo vuelve atrás
                navController.popBackStack()
            },
            onVolverClick = {
                navController.popBackStack()
            }
        )
    }
}

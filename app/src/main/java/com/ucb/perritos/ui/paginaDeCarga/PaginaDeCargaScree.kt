package com.ucb.perritos.ui.paginaDeCarga

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ucb.perritos.ui.components.AnimacionLogoCarga
import kotlinx.coroutines.delay

@Composable
fun PaginaDeCargaScreen(
    irBienvenida: () -> Unit
) {
    // apenas esta pantalla aparece, esperamos y luego navegamos:
    LaunchedEffect(Unit) {
        delay(2000) // 2 segundos de splash
        irBienvenida()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        AnimacionLogoCarga()
    }
}
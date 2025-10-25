package com.ucb.perritos.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ucb.perritos.R

@Composable
fun AnimacionLogoCarga(
    modifier: Modifier = Modifier
) {
    // animar escala: 1.0 -> 1.15 -> 1.0 -> ...
    val infiniteTransition = rememberInfiniteTransition(label = "logoAnim")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 600,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scaleAnim"
    )

    Image(
        painter = painterResource(id = R.drawable.icono_bienvenida),
        contentDescription = "Logo de la app",
        modifier = modifier
            .size(96.dp)
            .scale(scale)
    )
}
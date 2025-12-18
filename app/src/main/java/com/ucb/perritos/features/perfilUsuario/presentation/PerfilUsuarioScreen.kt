package com.ucb.perritos.features.perfilUsuario.presentation

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ucb.perritos.R
import com.ucb.perritos.features.registroUsuario.domain.model.UsuarioModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun PerfilUsuarioScreen(
    vm: PerfilUsuarioViewModel = koinViewModel(),
    onCerrarSesion: () -> Unit = {}
) {
    val state by vm.state.collectAsState()
    val orangePrimary = Color(0xFFF89A22)

    // Control para la animación de entrada
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(state) {
        if (state is PerfilUsuarioViewModel.PerfilUiState.Success) {
            visible = true
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            // 1. Fondo de patitas (Usando tu ic_paw)
            SubtlePawsBackground(color = orangePrimary)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Título con animación
                EntradaAnimada(visible = visible, delay = 100) {
                    Text(
                        text = stringResource(id = R.string.perfil_usuario_titulo_principal),
                        fontSize = 26.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = orangePrimary,
                        modifier = Modifier.padding(top = 16.dp, bottom = 32.dp)
                    )
                }

                when (val uiState = state) {
                    is PerfilUsuarioViewModel.PerfilUiState.Loading -> {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(color = orangePrimary)
                        }
                    }
                    is PerfilUsuarioViewModel.PerfilUiState.Error -> {
                        Text(text = uiState.mensaje, color = Color.Gray, modifier = Modifier.padding(top = 20.dp))
                    }
                    is PerfilUsuarioViewModel.PerfilUiState.Success -> {
                        PerfilContent(uiState.usuario, orangePrimary, visible)
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // Espacio para menú inferior
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}

@Composable
fun PerfilContent(usuario: UsuarioModel, primaryColor: Color, visible: Boolean) {
    // Animación de flotación para el avatar
    val infiniteTransition = rememberInfiniteTransition(label = "user_float")
    val floatY by infiniteTransition.animateFloat(
        initialValue = -10f, targetValue = 10f,
        animationSpec = infiniteRepeatable(tween(2000, easing = FastOutSlowInEasing), RepeatMode.Reverse), label = "y"
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // 1. Avatar Animado
        EntradaAnimada(visible = visible, delay = 300) {
            Box(
                modifier = Modifier
                    .size(130.dp)
                    .graphicsLayer { translationY = floatY }
                    .clip(CircleShape)
                    .background(primaryColor.copy(alpha = 0.1f))
                    .border(3.dp, primaryColor, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null,
                    tint = primaryColor,
                    modifier = Modifier.size(90.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // 2. InfoCards escalonadas
        EntradaAnimada(visible = visible, delay = 500) {
            InfoCard(
                icon = Icons.Default.Person,
                label = stringResource(id = R.string.perfil_usuario_card_label_nombre_duenio),
                value = usuario.nombreDueño ?: stringResource(id = R.string.perfil_usuario_card_valor_sin_nombre),
                primaryColor = primaryColor
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        EntradaAnimada(visible = visible, delay = 700) {
            InfoCard(
                icon = Icons.Default.Email,
                label = stringResource(id = R.string.perfil_usuario_card_label_correo_electronico),
                value = usuario.email ?: stringResource(id = R.string.perfil_usuario_card_valor_sin_email),
                primaryColor = primaryColor
            )
        }
    }
}

@Composable
fun InfoCard(icon: ImageVector, label: String, value: String, primaryColor: Color) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(primaryColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = primaryColor, modifier = Modifier.size(24.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = label, fontSize = 13.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
                Text(text = value, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2D3436))
            }
        }
    }
}


@Composable
private fun EntradaAnimada(visible: Boolean, delay: Int, content: @Composable () -> Unit) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(tween(600, delayMillis = delay)) +
                slideInVertically(
                    initialOffsetY = { 40 },
                    animationSpec = tween(600, delayMillis = delay)
                )
    ) { content() }
}

@Composable
private fun SubtlePawsBackground(color: Color) {
    val pawPainter = painterResource(id = R.drawable.ic_paw)
    val infiniteTransition = rememberInfiniteTransition(label = "paws")
    val moveX by infiniteTransition.animateFloat(
        initialValue = -20f, targetValue = 20f,
        animationSpec = infiniteRepeatable(tween(15000, easing = LinearEasing), RepeatMode.Reverse), label = ""
    )

    Canvas(modifier = Modifier.fillMaxSize().graphicsLayer { alpha = 0.06f }) {
        val sizePx = 50.dp.toPx()
        val positions = listOf(Offset(0.15f, 0.2f), Offset(0.8f, 0.15f), Offset(0.1f, 0.6f), Offset(0.85f, 0.85f))
        positions.forEachIndexed { i, pos ->
            val dir = if (i % 2 == 0) 1f else -1f
            translate(size.width * pos.x + (moveX * dir), size.height * pos.y) {
                with(pawPainter) { draw(size = Size(sizePx, sizePx), colorFilter = ColorFilter.tint(color)) }
            }
        }
    }
}
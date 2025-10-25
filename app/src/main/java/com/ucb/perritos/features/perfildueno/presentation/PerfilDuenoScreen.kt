package com.ucb.perritos.features.perfildueno.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.ucb.perritos.R

@Composable
fun PerfilDuenoScreen(viewModel: PerfilDuenoViewModel, onAccountDeleted: () -> Unit = {}) {
    // Cargar perfil al iniciar
    LaunchedEffect(Unit) { viewModel.cargarPerfil() }

    val perfil by viewModel.perfil.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    val accent = Color(0xFFFF9800)
    // val grayText = Color(0xFF666666) // no usado por ahora
    val cardBg = Color(0xFFF6F6F6)

    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        if (loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            return@Box
        }

        error?.let {
            Text(text = it, modifier = Modifier.align(Alignment.Center))
            return@Box
        }

        Column(modifier = Modifier.fillMaxSize()) {
            // Header: avatar + name
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = null,
                    modifier = Modifier
                        .size(92.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = perfil?.nombre ?: "Pedro Dueño",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = accent,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Coocker", fontSize = 13.sp, color = Color(0xFF4A90E2))
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Section header
            SectionHeader(title = "Información de usuario", accent = accent)

            Spacer(modifier = Modifier.height(12.dp))

            // Card with info rows
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(cardBg, RoundedCornerShape(10.dp))
                    .padding(16.dp)
            ) {
                InfoRow(
                    icon = "✉️",
                    label = "Email:",
                    value = perfil?.email ?: "prueba@gmail.com",
                    tint = accent
                )

                Spacer(modifier = Modifier.height(12.dp))

                InfoRow(
                    icon = "👤",
                    label = "Nombre del dueño",
                    value = perfil?.nombre ?: "Pedro Dueño",
                    tint = accent
                )

                Spacer(modifier = Modifier.height(12.dp))

                InfoRow(
                    icon = "📍",
                    label = "ID GPS",
                    value = perfil?.id ?: "20",
                    tint = accent
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Button(
                        onClick = { /* TODO: actualizar */ },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = accent)
                    ) {
                        Text(text = "Actualizar datos", color = Color.White)
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    OutlinedButton(
                        onClick = {
                            // Eliminar cuenta local y volver a pantalla de Login
                            viewModel.eliminarCuenta {
                                onAccountDeleted()
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(8.dp),
                        // outline color not directly available without colors param, keep default

                    ) {
                        Text(text = "Eliminar cuenta", color = accent)
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Bottom spacing for nav bar
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
private fun SectionHeader(title: String, accent: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(accent.copy(alpha = 0.12f), shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "i", color = accent, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.width(12.dp))

        Box(
            modifier = Modifier
                .height(36.dp)
                .fillMaxWidth()
                .background(Color(0xFFF3F3F3), shape = RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(text = title, modifier = Modifier.padding(start = 16.dp), fontWeight = FontWeight.SemiBold, color = accent)
        }
    }
}

@Composable
private fun InfoRow(icon: String, label: String, value: String, tint: Color) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(tint.copy(alpha = 0.12f), shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(text = icon, color = tint)
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(text = label, color = Color(0xFF666666))
        }

        Text(text = value, modifier = Modifier.weight(1f), textAlign = TextAlign.End)
    }
}

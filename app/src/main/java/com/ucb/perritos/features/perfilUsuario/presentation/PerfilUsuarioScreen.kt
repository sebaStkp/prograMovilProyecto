package com.ucb.perritos.features.perfilUsuario.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.ucb.perritos.features.registroUsuario.domain.model.UsuarioModel
import com.ucb.perritos.features.registroUsuario.domain.usecase.GetUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun PerfilUsuarioScreen(
    vm: PerfilUsuarioViewModel = koinViewModel(),
    onCerrarSesion: () -> Unit = {} // Callback por si quieres implementar logout
) {
    val state by vm.state.collectAsState()
    val orangePrimary = Color(0xFFF89A22)



    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Mi Perfil",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = orangePrimary,
                modifier = Modifier.padding(top = 16.dp, bottom = 32.dp)
            )

            when (val uiState = state) {
                is PerfilUsuarioViewModel.PerfilUiState.Loading -> {
                    CircularProgressIndicator(color = orangePrimary)
                }
                is PerfilUsuarioViewModel.PerfilUiState.Error -> {
                    Text(text = uiState.mensaje, color = Color.Gray)
                }
                is PerfilUsuarioViewModel.PerfilUiState.Success -> {
                    PerfilContent(uiState.usuario, orangePrimary)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

//            // Botón de Cerrar Sesión (Opcional)
//            Button(
//                onClick = onCerrarSesion,
//                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFEBEE)),
//                modifier = Modifier.fillMaxWidth().height(50.dp),
//                shape = RoundedCornerShape(12.dp)
//            ) {
//                Icon(Icons.Default.Logout, contentDescription = null, tint = Color.Red)
//                Spacer(modifier = Modifier.width(8.dp))
//                Text("Cerrar Sesión", color = Color.Red, fontWeight = FontWeight.Bold)
//            }
            // Espacio para que no choque con el menú inferior
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
fun PerfilContent(usuario: UsuarioModel, primaryColor: Color) {
    // 1. Avatar
    Box(
        modifier = Modifier
            .size(120.dp)
            .clip(CircleShape)
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        // CORRECCIÓN: Restaurada la lógica para mostrar imagen o icono

            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(80.dp)
            )

    }

    Spacer(modifier = Modifier.height(24.dp))

    // 2. Datos
    InfoCard(
        icon = Icons.Default.Person,
        label = "Nombre del Dueño",
        value = usuario.nombreDueño ?: "Sin nombre",
        primaryColor = primaryColor
    )

    Spacer(modifier = Modifier.height(16.dp))

    InfoCard(
        icon = Icons.Default.Email,
        label = "Correo Electrónico",
        value = usuario.email ?: "Sin email",
        primaryColor = primaryColor
    )
}

@Composable
fun InfoCard(icon: ImageVector, label: String, value: String, primaryColor: Color) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(primaryColor.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = primaryColor)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = label, fontSize = 12.sp, color = Color.Gray)
                Text(text = value, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF424242))
            }
        }
    }
}
package com.ucb.perritos.features.perfilPerro.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import androidx.compose.runtime.getValue

import org.koin.androidx.compose.koinViewModel
//modifier: Modifier = Modifier,
//    vm: RegistroPerroViewModel = koinViewModel()
@Composable
fun PerfilPerroScreen(modifier: Modifier = Modifier,vm: PerfilPerroViewModel = koinViewModel(), perroId: Long = 1L) {
    val state by vm.state.collectAsState()

    LaunchedEffect(perroId) { vm.init(perroId) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 40.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                )
                .align(Alignment.BottomCenter)
                .verticalScroll(rememberScrollState())
                .padding(bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(Modifier.height(16.dp))
            Text(
                text = "Perfil de la mascota",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(16.dp))
            AsyncImage(
                model = state.perfil?.avatarUrl,
                contentDescription = "Avatar perro",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.height(12.dp))
            Text(
                text = state.perfil?.nombre ?: "—",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = state.perfil?.raza ?: "",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.height(16.dp))
            // Acciones (dummy por ahora)
            Column(Modifier.fillMaxWidth().padding(horizontal = 24.dp)) {
                OutlinedButton(
                    onClick = { /* TODO: navegar a Datos de la mascota */ },
                    modifier = Modifier.fillMaxWidth()
                ) { Text("Datos de la mascota") }

                Spacer(Modifier.height(8.dp))
                OutlinedButton(
                    onClick = { /* TODO: navegar a Medallas */ },
                    modifier = Modifier.fillMaxWidth()
                ) { Text("Medallas") }

                Spacer(Modifier.height(8.dp))
                OutlinedButton(
                    onClick = { /* TODO: navegar a Familiares */ },
                    modifier = Modifier.fillMaxWidth()
                ) { Text("Familiares") }
            }

            Spacer(Modifier.height(16.dp))
            Text(
                "FOTOS DEL PERRO (próxima iteración)",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(horizontal = 24.dp)
            )

            Spacer(Modifier.height(12.dp))
            Button(
                onClick = { /* TODO: abrir picker y agregar foto */ },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .height(50.dp)
            ) {
                Text("Añadir foto")
            }

            if (state.loading) {
                Spacer(Modifier.height(16.dp))
                LinearProgressIndicator(Modifier.fillMaxWidth().padding(horizontal = 24.dp))
            }
        }
    }
}

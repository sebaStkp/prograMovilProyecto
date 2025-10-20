package com.ucb.perritos.features.registroUsuario.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ucb.perritos.R
import com.ucb.perritos.features.registroMascota.presentation.RegistroPerroViewModel
import org.koin.androidx.compose.koinViewModel
import kotlin.Unit

@Composable
fun RegistroUsuarioScreen(
    onVolverClick: () -> Unit
) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo superior
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icono_bienvenida), // tu ícono del perro
                    contentDescription = "Logo Find your dog",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                androidx.compose.material3.Text(
                    text = "Find your ",
                    color = androidx.compose.ui.graphics.Color.Gray,
                    style = MaterialTheme.typography.titleLarge
                )
                androidx.compose.material3.Text(
                    text = "dog",
                    color = androidx.compose.ui.graphics.Color(0xFFF5A623),
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            RegistroForm(
                onVolverClick = {
                        onVolverClick()
                }
            )

        }
    }
}

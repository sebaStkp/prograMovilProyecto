package com.ucb.perritos.features.registroMascota.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.ucb.perritos.features.registroMascota.domain.model.RegistroPerroModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegistroPerroScreen(
    modifier: Modifier = Modifier,
    vm: RegistroPerroViewModel = koinViewModel()
) {
    var nombrePerro by remember { mutableStateOf("") }
    var raza by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }

    val state by vm.state.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = nombrePerro,
                onValueChange = { nombrePerro = it },
                label = { Text("Nombre del Perro") }
            )
            OutlinedTextField(
                value = raza,
                onValueChange = { raza = it },
                label = { Text("Raza") }
            )
            OutlinedTextField(
                value = edad,
                onValueChange = { edad = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text("Edad") }
            )
            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripcion") }
            )

            OutlinedButton(onClick = { vm.registrarPerro(RegistroPerroModel(nombrePerro, raza, edad.toInt(), descripcion)) }) {
                Text("Registrar")
            }

            when (val st = state) {
                is RegistroPerroViewModel.RegistrarPerroStateUI.Error -> {
                    Text("Error: ${st.message}")
                }

                RegistroPerroViewModel.RegistrarPerroStateUI.Init -> {
                    Text("Ingrese los datos para registrar.")
                }

                RegistroPerroViewModel.RegistrarPerroStateUI.Loading -> {
                    Text("Cargando...")
                }

                is RegistroPerroViewModel.RegistrarPerroStateUI.Success -> {
                    Text("Perro registrado correctamente")
                }
            }
        }
    }

}
package com.ucb.perritos.features.registroMascota.presentation


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults

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

import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ucb.perritos.R
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
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 40.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color(0xFFF5F5F5),
                    shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                )
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Registro de Mascota",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = nombrePerro,
                onValueChange = { nombrePerro = it },
                label = { Text("Nombre del perro") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = raza,
                onValueChange = { raza = it },
                label = { Text("Raza") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = edad,
                onValueChange = { edad = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text("Edad") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    vm.registrarPerro(
                        RegistroPerroModel(
                            nombrePerro = nombrePerro,
                            raza = raza,
                            edad = edad.toIntOrNull() ?: 0,
                            descripcion = descripcion
                        )
                    )
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .height(50.dp)
            ) {
                Text(text = "Regístrame", color = Color.White)
            }
            Spacer(modifier = Modifier.height(16.dp))
            when (val st = state) {
                is RegistroPerroViewModel.RegistrarPerroStateUI.Error -> {
                    Text("Error: ${st.message}", color = Color.Red)
                }
                is RegistroPerroViewModel.RegistrarPerroStateUI.Init -> {
                    Text("Ingrese los datos para registrar.", color = Color.Gray)
                }
                is RegistroPerroViewModel.RegistrarPerroStateUI.Loading -> {
                    Text("Cargando...", color = Color.Gray)
                }
                is RegistroPerroViewModel.RegistrarPerroStateUI.Success -> {
                    Text("Perro registrado correctamente", color = Color(0xFFFF9800))
                }

            }
        }
    }
}

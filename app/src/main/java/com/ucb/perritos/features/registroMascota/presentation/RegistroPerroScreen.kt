package com.ucb.perritos.features.registroMascota.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ucb.perritos.features.registroMascota.domain.model.RegistroPerroModel
import org.koin.androidx.compose.koinViewModel


private val OrangePrimary = Color(0xFFF89A22)
private val TextBlueGray = Color(0xFF6A8693)
private val WhiteBackground = Color.White

@Composable
fun RegistroPerroScreen(
    vm: RegistroPerroViewModel = koinViewModel(),
    irMapa: () -> Unit
) {
    var nombrePerro by remember { mutableStateOf("") }
    var raza by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }

    val state by vm.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(state) {
        when (val st = state) {
            is RegistroPerroViewModel.RegistrarPerroStateUI.Success -> {
                Toast.makeText(context, "¡Mascota registrada con éxito!", Toast.LENGTH_SHORT).show()
                irMapa()
            }
            is RegistroPerroViewModel.RegistrarPerroStateUI.Error -> {
                Toast.makeText(context, "Error: ${st.message}", Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }





    Surface(
        modifier = Modifier.fillMaxSize(),
        color = WhiteBackground
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {



            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),


                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                PetPhotoSection()

                Spacer(modifier = Modifier.height(16.dp))


                Text(
                    text = "Registro de Mascota",
                    color = OrangePrimary,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))


                MascotaCustomTextField(
                    label = "Nombre del perro",
                    value = nombrePerro,
                    onValueChange = { nombrePerro = it }
                )

                Spacer(modifier = Modifier.height(16.dp))

                MascotaCustomTextField(
                    label = "Raza",
                    value = raza,
                    onValueChange = { raza = it }
                )

                Spacer(modifier = Modifier.height(16.dp))

                MascotaCustomTextField(
                    label = "Edad",
                    value = edad,
                    onValueChange = { edad = it },
                    keyboardType = KeyboardType.Number
                )

                Spacer(modifier = Modifier.height(16.dp))

                MascotaCustomTextField(
                    label = "Descripcion",
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    singleLine = false,
                    maxLines = 3
                )

                Spacer(modifier = Modifier.height(32.dp))


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
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(25.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = OrangePrimary,
                        contentColor = Color.White
                    ),
                    enabled = state !is RegistroPerroViewModel.RegistrarPerroStateUI.Loading
                ) {
                    if (state is RegistroPerroViewModel.RegistrarPerroStateUI.Loading) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                    } else {
                        Text(
                            text = "Registrarme",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}



@Composable
fun PetPhotoSection() {

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(110.dp)
            .background(WhiteBackground, CircleShape)
    ) {
        Box(contentAlignment = Alignment.BottomEnd) {

            Box(
                modifier = Modifier
                    .size(100.dp)
                    .border(2.dp, OrangePrimary, CircleShape)
                    .padding(4.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE0E0E0)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Foto Mascota",
                    tint = TextBlueGray,
                    modifier = Modifier.size(70.dp)
                )
            }


            Box(
                modifier = Modifier
                    .size(30.dp)
                    .offset(x = 4.dp, y = 4.dp)
                    .background(WhiteBackground, CircleShape)
                    .border(1.dp, OrangePrimary, CircleShape)
                    .padding(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowUpward,
                    contentDescription = "Subir foto",
                    tint = OrangePrimary,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun MascotaCustomTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = true,
    maxLines: Int = 1
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            color = TextBlueGray,
            fontSize = 14.sp,
            modifier = Modifier.padding(start = 4.dp, bottom = 6.dp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = OrangePrimary,
                unfocusedBorderColor = OrangePrimary,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                cursorColor = OrangePrimary,
                focusedTextColor = TextBlueGray,
                unfocusedTextColor = TextBlueGray
            ),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            singleLine = singleLine,
            maxLines = maxLines
        )
    }
}
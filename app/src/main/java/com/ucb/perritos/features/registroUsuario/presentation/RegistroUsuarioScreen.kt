package com.ucb.perritos.features.registroUsuario.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ucb.perritos.R
import com.ucb.perritos.features.registroUsuario.domain.model.UsuarioModel
import com.ucb.perritos.features.registroUsuario.presentation.RegistroUsuarioViewModel
import org.koin.androidx.compose.koinViewModel


val OrangePrimary = Color(0xFFF89A22)
val TextBlueGray = Color(0xFF6A8693)
val WhiteBackground = Color.White

@Composable
fun RegistroUsuarioScreen(
    vm: RegistroUsuarioViewModel = koinViewModel(),
    onVolverClick: () -> Unit,
    irLogin: () -> Unit

) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = WhiteBackground
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(30.dp))

            ProfilePhotoSection()
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Registro de Usuario",
                color = OrangePrimary,
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(30.dp))


            RegistroFormContent(vm, onVolverClick, irLogin )
        }
    }
}


@Composable
fun ProfilePhotoSection() {
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
                contentDescription = "Foto de perfil",
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
                imageVector = Icons.Default.FileUpload,
                contentDescription = "Subir foto",
                tint = OrangePrimary,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun RegistroFormContent(
    vm: RegistroUsuarioViewModel,
    onVolverClick: () -> Unit,
    irLogin: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmarPassword by remember { mutableStateOf("") }

    val state by vm.state.collectAsState()

    Column(modifier = Modifier.fillMaxWidth()) {


        CustomTextField(
            label = "Nombre del dueño",
            value = nombre,
            onValueChange = { nombre = it }
        )

        Spacer(modifier = Modifier.height(16.dp))


        CustomTextField(
            label = "Email",
            value = correo,
            onValueChange = { correo = it },
            keyboardType = KeyboardType.Email
        )

        Spacer(modifier = Modifier.height(16.dp))


        CustomTextField(
            label = "Contraseña",
            value = password,
            onValueChange = { password = it },
            isPassword = true
        )

        Spacer(modifier = Modifier.height(16.dp))


        CustomTextField(
            label = "Confirmar contraseña",
            value = confirmarPassword,
            onValueChange = { confirmarPassword = it },
            isPassword = true
        )

        Spacer(modifier = Modifier.height(40.dp))


        Button(
            onClick = {
                if (password == confirmarPassword) {
                    vm.registrarUsuario(UsuarioModel(nombre, correo, password))
                } else {

                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = OrangePrimary,
                contentColor = Color.White
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
        ) {
            Text(
                text = "Registrarme",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }


        Spacer(modifier = Modifier.height(16.dp))

        when (val st = state) {
            is RegistroUsuarioViewModel.RegistrarUsuarioStateUI.Error -> {
                Text(
                    text = st.message,
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            is RegistroUsuarioViewModel.RegistrarUsuarioStateUI.Loading -> {
                CircularProgressIndicator(
                    color = OrangePrimary,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            is RegistroUsuarioViewModel.RegistrarUsuarioStateUI.Success -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "¡Cuenta creada!",
                        color = OrangePrimary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = st.message,
                        color = TextBlueGray,
                        fontSize = 14.sp,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                    TextButton(onClick = irLogin) {
                        Text("Ir a Iniciar Sesión", color = OrangePrimary)
                    }
                }
            }
            else -> {}
        }
    }
}


@Composable
fun CustomTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text
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
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            singleLine = true
        )
    }
}
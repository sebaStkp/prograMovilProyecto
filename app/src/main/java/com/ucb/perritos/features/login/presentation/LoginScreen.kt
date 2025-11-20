package com.ucb.perritos.features.login.presentation

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ucb.perritos.R
import com.ucb.perritos.features.login.presentation.LoginViewModel
import org.koin.androidx.compose.koinViewModel

// --- COLORES DEL DISEÑO ---
private val OrangePrimary = Color(0xFFF89A22)
private val TextBlueGray = Color(0xFF6A8693)
private val HeaderGray = Color(0xFFF2F2F2)

@Composable
fun LoginScreen(
    vm: LoginViewModel = koinViewModel(),
    irRegistroCuenta: () -> Unit,
    irRegistroMascota: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val state by vm.state.collectAsState()
    val context = LocalContext.current

    // Escuchar el estado para navegación o errores
    LaunchedEffect(state) {
        when (val st = state) {
            is LoginViewModel.LoginStateUI.Success -> {
                Toast.makeText(context, st.mensaje, Toast.LENGTH_SHORT).show()
                irRegistroMascota()
            }
            is LoginViewModel.LoginStateUI.Error -> {
                Toast.makeText(context, st.message, Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()), // Scroll por si el teclado tapa campos
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Contenedor del formulario (Subimos un poco con offset negativo para acercarlo a la onda)
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .offset(y = (-10).dp)
            ) {
                Spacer(modifier = Modifier.height(10.dp))

                // 2. TÍTULOS
                Text(
                    text = "Inicio de sesión",
                    color = OrangePrimary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Bienvenido de nuevo!!",
                    color = TextBlueGray,
                    fontSize = 14.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp))

                // 3. INPUTS (Estilo Labels Afuera)
                LoginCustomTextField(
                    label = "Email",
                    value = email,
                    onValueChange = { email = it },
                    keyboardType = KeyboardType.Email
                )

                Spacer(modifier = Modifier.height(16.dp))

                LoginCustomTextField(
                    label = "Contraseña",
                    value = password,
                    onValueChange = { password = it },
                    isPassword = true
                )

                // 4. OLVIDASTE CONTRASEÑA (Alineado a la derecha)
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    TextButton(onClick = { /* Acción Olvidé contraseña */ }) {
                        Text(
                            text = "Olvidaste tu contraseña?",
                            color = OrangePrimary,
                            fontSize = 14.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 5. BOTÓN INGRESAR
                Button(
                    onClick = { vm.iniciarSesion(email, password) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(25.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = OrangePrimary,
                        contentColor = Color.White
                    ),
                    enabled = state !is LoginViewModel.LoginStateUI.Loading
                ) {
                    if (state is LoginViewModel.LoginStateUI.Loading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(
                            text = "Ingresar", // En tu diseño dice "Registrarme" pero es confuso para Login
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // 6. CREAR CUENTA LINK
                Text(
                    text = "Crearme una cuenta",
                    color = TextBlueGray,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { irRegistroCuenta() }
                        .padding(8.dp),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

// --- HEADER CON FONDO ONDULADO ---

// --- COMPONENTE INPUT PERSONALIZADO ---
@Composable
fun LoginCustomTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        // Etiqueta arriba
        Text(
            text = label,
            color = TextBlueGray,
            fontSize = 14.sp,
            modifier = Modifier.padding(start = 4.dp, bottom = 6.dp)
        )

        // Campo con borde naranja
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
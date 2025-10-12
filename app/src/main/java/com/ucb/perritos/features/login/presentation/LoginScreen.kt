package com.ucb.perritos.features.login.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.androidx.compose.koinViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.border

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextFieldDefaults

import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged

import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation




@Composable
fun LoginScreen(
    vm: LoginViewModel = koinViewModel(),
    navigateLogin: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isEmailFocused by remember { mutableStateOf(false) }
    var isPasswordFocused by remember { mutableStateOf(false) }

    val state = vm.state.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Inicio de sesión",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFF9800),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Bienvenido de nueva!!",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                textAlign = TextAlign.Center
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = {
                    Text(
                        text = "Email",
                        color = Color(0xFF437D99),
                        fontWeight = FontWeight.Light,
                        fontSize = 14.sp
                    )
                },
                        textStyle = LocalTextStyle.current.copy(color = Color(0xFF437D99)),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFFEF4E7))
                    .onFocusChanged { focusState ->
                        isEmailFocused = focusState.isFocused
                    },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFA9114),
                    unfocusedBorderColor = Color.Transparent,
                    focusedContainerColor = Color(0xFFFEF4E7),
                    unfocusedContainerColor = Color(0xFFFEF4E7),
                    focusedLabelColor = Color(0xFF437D99),
                    unfocusedLabelColor = Color(0xFF437D99),
                    focusedTextColor = Color(0xFF437D99),
                    unfocusedTextColor = Color(0xFF437D99),
                    focusedPlaceholderColor = Color(0xFF437D99),
                    unfocusedPlaceholderColor = Color(0xFF437D99),
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña",
                    color = Color(0xFF437D99),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light
                )
                        },
                textStyle = LocalTextStyle.current.copy(color = Color(0xFF437D99)),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFFEF4E7))
                    .onFocusChanged { focusState ->
                        isPasswordFocused = focusState.isFocused
                    },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFA9114),
                    unfocusedBorderColor = Color.Transparent,
                    focusedContainerColor = Color(0xFFFEF4E7),
                    unfocusedContainerColor = Color(0xFFFEF4E7),
                    focusedLabelColor = Color(0xFF437D99),
                    unfocusedLabelColor = Color(0xFF437D99),
                    focusedTextColor = Color(0xFF437D99),
                    unfocusedTextColor = Color(0xFF437D99),
                    focusedPlaceholderColor = Color(0xFF437D99),
                    unfocusedPlaceholderColor = Color(0xFF437D99),
                ),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )


            Spacer(modifier = Modifier.height(20.dp))


            ClickableText(
                text = AnnotatedString("Olvidaste tu contraseña?"),
                onClick = {

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp),
                style = LocalTextStyle.current.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFFFF9800),
                    textAlign = TextAlign.End
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { vm.setToken(email) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(10.dp))
            ) {
                Text(text = "Registrarme", color = Color.White)
            }
            Spacer(modifier = Modifier.height(20.dp))


            ClickableText(
                text = AnnotatedString("Crearme una cuenta"),
                onClick = { navigateLogin() },
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color(0xFF437D99),
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}

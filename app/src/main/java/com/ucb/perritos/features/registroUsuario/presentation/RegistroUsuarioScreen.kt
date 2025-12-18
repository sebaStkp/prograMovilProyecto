package com.ucb.perritos.features.registroUsuario.presentation

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ucb.perritos.R
import com.ucb.perritos.features.registroUsuario.domain.model.UsuarioModel
import org.koin.androidx.compose.koinViewModel

val OrangePrimary = Color(0xFFF89A22)
val TextBlueGray = Color(0xFF6A8693)

@Composable
fun RegistroUsuarioScreen(
    vm: RegistroUsuarioViewModel = koinViewModel(),
    onVolverClick: () -> Unit,
    irLogin: () -> Unit
) {

    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(modifier = Modifier.fillMaxSize()) {


            SubtlePawsBackground(color = OrangePrimary)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(30.dp))


                EntradaAnimada(visible = visible, delay = 100) {
                    ProfilePhotoSection()
                }

                Spacer(modifier = Modifier.height(16.dp))


                EntradaAnimada(visible = visible, delay = 250) {
                    Text(
                        text = stringResource(id = R.string.registro_usuario_titulo_principal),
                        color = OrangePrimary,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))


                RegistroFormContent(vm, visible, irLogin)
            }
        }
    }
}

@Composable
fun RegistroFormContent(
    vm: RegistroUsuarioViewModel,
    visible: Boolean,
    irLogin: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmarPassword by remember { mutableStateOf("") }

    val state by vm.state.collectAsState()

    Column(modifier = Modifier.fillMaxWidth()) {


        EntradaAnimada(visible = visible, delay = 400) {
            CustomTextField(
                label = stringResource(id = R.string.registro_usuario_tf_label_nombre_duenio),
                value = nombre,
                onValueChange = { nombre = it }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        EntradaAnimada(visible = visible, delay = 550) {
            CustomTextField(
                label = stringResource(id = R.string.registro_usuario_tf_label_email),
                value = correo,
                onValueChange = { correo = it },
                keyboardType = KeyboardType.Email
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        EntradaAnimada(visible = visible, delay = 700) {
            CustomTextField(
                label = stringResource(id = R.string.registro_usuario_tf_label_contrasenia),
                value = password,
                onValueChange = { password = it },
                isPassword = true
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        EntradaAnimada(visible = visible, delay = 850) {
            CustomTextField(
                label = stringResource(id = R.string.registro_usuario_tf_label_confirmar_contrasenia),
                value = confirmarPassword,
                onValueChange = { confirmarPassword = it },
                isPassword = true
            )
        }

        Spacer(modifier = Modifier.height(40.dp))


        EntradaAnimada(visible = visible, delay = 1000) {
            Button(
                onClick = {
                    if (password == confirmarPassword) {
                        vm.registrarUsuario(UsuarioModel(nombre, correo, password))
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .graphicsLayer {
                        shadowElevation = 8f
                        shape = RoundedCornerShape(27.dp)
                        clip = true
                    },
                shape = RoundedCornerShape(27.dp),
                colors = ButtonDefaults.buttonColors(containerColor = OrangePrimary),
                enabled = state !is RegistroUsuarioViewModel.RegistrarUsuarioStateUI.Loading
            ) {
                Text(
                    text = stringResource(id = R.string.registro_usuario_btn_registrarme),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))


        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            when (val st = state) {
                is RegistroUsuarioViewModel.RegistrarUsuarioStateUI.Loading -> {
                    CircularProgressIndicator(color = OrangePrimary)
                }
                is RegistroUsuarioViewModel.RegistrarUsuarioStateUI.Error -> {
                    Text(text = st.message, color = Color.Red, textAlign = TextAlign.Center)
                }
                is RegistroUsuarioViewModel.RegistrarUsuarioStateUI.Success -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "¡Cuenta creada!", color = OrangePrimary, fontWeight = FontWeight.Bold)
                        TextButton(onClick = irLogin) {
                            Text(text = "Ir al Login", color = OrangePrimary)
                        }
                    }
                }
                else -> {}
            }
        }
    }
}


@Composable
fun EntradaAnimada(visible: Boolean, delay: Int, content: @Composable () -> Unit) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(tween(600, delayMillis = delay)) +
                slideInVertically(
                    initialOffsetY = { 40 },
                    animationSpec = tween(600, delayMillis = delay)
                )

    ) { content() }
}

@Composable
private fun SubtlePawsBackground(color: Color) {
    val pawPainter = painterResource(id = R.drawable.ic_paw)
    val infiniteTransition = rememberInfiniteTransition(label = "bg")
    val move = infiniteTransition.animateFloat(
        initialValue = -20f, targetValue = 20f,
        animationSpec = infiniteRepeatable(tween(12000, easing = LinearEasing), RepeatMode.Reverse), label = ""
    )

    Canvas(modifier = Modifier.fillMaxSize().graphicsLayer { alpha = 0.07f }) {
        val sizePx = 50.dp.toPx()
        val positions = listOf(Offset(0.1f, 0.2f), Offset(0.9f, 0.15f), Offset(0.2f, 0.5f), Offset(0.85f, 0.8f))
        positions.forEachIndexed { i, pos ->
            translate(size.width * pos.x + move.value, size.height * pos.y) {
                with(pawPainter) { draw(size = Size(sizePx, sizePx), colorFilter = ColorFilter.tint(color)) }
            }
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
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = OrangePrimary,
                unfocusedBorderColor = Color.LightGray,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color(0xFFF9F9F9)
            ),
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            singleLine = true
        )
    }
}

@Composable
fun ProfilePhotoSection() {
    Box(
        modifier = Modifier
            .size(110.dp)
            .border(3.dp, OrangePrimary, CircleShape)
            .padding(6.dp)
            .clip(CircleShape)
            .background(Color(0xFFF0F0F0)),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = null,
            tint = TextBlueGray,
            modifier = Modifier.size(75.dp)
        )
    }
}
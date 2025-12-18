package com.ucb.perritos.features.login.presentation

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.platform.LocalContext
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
import org.koin.androidx.compose.koinViewModel
import java.lang.Math.toRadians

private val OrangePrimary = Color(0xFFF89A22)
private val TextBlueGray = Color(0xFF6A8693)

@Composable
fun LoginScreen(
    vm: LoginViewModel = koinViewModel(),
    irRegistroCuenta: () -> Unit,
    irRegistroMascota: () -> Unit,
    irMisPerros: () -> Unit,
    irMapa: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val state by vm.state.collectAsState()
    val context = LocalContext.current


    var animarEntrada by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { animarEntrada = true }

    LaunchedEffect(state) {
        when (val st = state) {
            is LoginViewModel.LoginStateUI.Success -> {
                Toast.makeText(context, st.mensaje, Toast.LENGTH_SHORT).show()
                if (st.irAlMapa) irMisPerros() else irRegistroMascota()
            }
            is LoginViewModel.LoginStateUI.Error -> {
                Toast.makeText(context, st.message, Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(modifier = Modifier.fillMaxSize()) {


            SubtlePawsBackground(color = OrangePrimary)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {


                AnimatedVisibility(
                    visible = animarEntrada,
                    enter = slideInVertically(initialOffsetY = { -50 }) + fadeIn(tween(600))
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = stringResource(id = R.string.login_titulo_principal),

                            color = OrangePrimary,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.ExtraBold,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = stringResource(id = R.string.login_subtitulo_bienvenido),
                            color = TextBlueGray,
                            fontSize = 15.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))


                EntradaAnimada(visible = animarEntrada, delay = 200) {
                    LoginCustomTextField(
                        label = stringResource(id = R.string.login_tf_label_email),
                        value = email,
                        onValueChange = { email = it },
                        keyboardType = KeyboardType.Email
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                EntradaAnimada(visible = animarEntrada, delay = 400) {
                    LoginCustomTextField(
                        label = stringResource(id = R.string.login_tf_label_contrasenia),
                        value = password,
                        onValueChange = { password = it },
                        isPassword = true
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))


                EntradaAnimada(visible = animarEntrada, delay = 600) {
                    Button(
                        onClick = { vm.iniciarSesion(email, password) },
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
                        enabled = state !is LoginViewModel.LoginStateUI.Loading
                    ) {
                        if (state is LoginViewModel.LoginStateUI.Loading) {
                            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                        } else {
                            Text(
                                text = stringResource(id = R.string.login_btn_ingresar),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))


                EntradaAnimada(visible = animarEntrada, delay = 800) {
                    Text(
                        text = stringResource(id = R.string.login_txt_crear_cuenta),
                        color = TextBlueGray,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .clickable { irRegistroCuenta() }
                            .padding(10.dp),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Medium
                    )
                }
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
    val infiniteTransition = rememberInfiniteTransition(label = "paws_breathing")


    val moveAmount by infiniteTransition.animateFloat(
        initialValue = -25f,
        targetValue = 25f,
        animationSpec = infiniteRepeatable(
            animation = tween(10000, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "move"
    )


    val scaleFactor by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "scale"
    )


    val rotationWiggle by infiniteTransition.animateFloat(
        initialValue = -5f,
        targetValue = 5f,
        animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "wiggle"
    )

    Canvas(modifier = Modifier
        .fillMaxSize()
        .graphicsLayer { alpha = 0.07f }
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        val positions = listOf(
            Triple(0.15f, 0.15f, -20f),
            Triple(0.80f, 0.10f, 15f),
            Triple(0.10f, 0.50f, -40f),
            Triple(0.90f, 0.60f, 45f),
            Triple(0.20f, 0.85f, 10f),
            Triple(0.75f, 0.90f, -15f)
        )

        positions.forEachIndexed { index, (relX, relY, baseRotation) ->

            val isEven = index % 2 == 0
            val individualOffset = if (isEven) moveAmount else -moveAmount
            val individualScale = if (isEven) scaleFactor else (2.0f - scaleFactor)

            val sizePx = (50.dp + (index * 5).dp).toPx() * individualScale

            rotate(
                degrees = baseRotation + (rotationWiggle * (if (isEven) 1 else -1)),
                pivot = Offset(canvasWidth * relX, canvasHeight * relY)
            ) {
                translate(
                    left = (canvasWidth * relX - sizePx / 2) + individualOffset,
                    top = (canvasHeight * relY - sizePx / 2) + (individualOffset * 0.5f)
                ) {
                    with(pawPainter) {
                        draw(
                            size = Size(sizePx, sizePx),
                            colorFilter = ColorFilter.tint(color)
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun LoginCustomTextField(
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
                unfocusedContainerColor = Color(0xFFF9F9F9),
                cursorColor = OrangePrimary
            ),
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            singleLine = true
        )
    }
}
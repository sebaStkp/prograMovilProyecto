package com.ucb.perritos.features.bienvenida.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.ucb.perritos.R
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel

@Composable
fun BienvenidaScreen(
    vm: BienvenidaViewModel = koinViewModel(),
    navigateLogin: () -> Unit,
    navigateRegistroUsuario: () -> Unit
) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }
    val infiniteTransitionIcon = rememberInfiniteTransition(label = "flotado_icono")

    val iconTranslateY by infiniteTransitionIcon.animateFloat(
        initialValue = -20f,
        targetValue = 20f,
        animationSpec = infiniteRepeatable(
            animation = tween(2500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "y"
    )

    val iconRotation by infiniteTransitionIcon.animateFloat(
        initialValue = -3f,
        targetValue = 3f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "rotation"
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            SubtlePawsBackground(color = Color(0xFFF5A623))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = R.drawable.icono_bienvenida,
                    contentDescription = stringResource(id = R.string.bienvenida_img_cd_ubicacion_mascotas),
                    modifier = Modifier
                        .width(220.dp)
                        .height(160.dp)
                        .graphicsLayer {
                            translationY = iconTranslateY
                            rotationZ = iconRotation
                        }
                )

                Spacer(modifier = Modifier.height(60.dp))
                AnimatedVisibility(
                    visible = visible,
                    enter = fadeIn(animationSpec = tween(1200)) +
                            slideInVertically(initialOffsetY = { 60 }, animationSpec = tween(1000))
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Button(
                            onClick = { navigateRegistroUsuario() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 32.dp)
                                .height(52.dp)
                                .graphicsLayer {
                                    shadowElevation = 4f
                                    shape = RoundedCornerShape(26.dp)
                                },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFF5A623),
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(26.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.bienvenida_btn_registro_usuario),
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        val annotatedText = buildAnnotatedString {
                            append(stringResource(id = R.string.bienvenida_txt_tienes_cuenta))
                            append(" ")
                            withStyle(style = SpanStyle(color = Color(0xFF1E88E5), fontWeight = FontWeight.ExtraBold)) {
                                append(stringResource(id = R.string.bienvenida_txt_iniciar_sesion))
                            }
                        }
                        Text(
                            text = annotatedText,
                            fontSize = 15.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .clickable { navigateLogin() }
                                .padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SubtlePawsBackground(color: Color) {
    val pawPainter = painterResource(id = R.drawable.ic_paw)
    val infiniteTransition = rememberInfiniteTransition(label = "paws_bg")

    val moveAmount by infiniteTransition.animateFloat(
        initialValue = -25f, targetValue = 25f,
        animationSpec = infiniteRepeatable(tween(12000, easing = LinearOutSlowInEasing), RepeatMode.Reverse),
        label = "move"
    )

    val scaleFactor by infiniteTransition.animateFloat(
        initialValue = 0.85f, targetValue = 1.15f,
        animationSpec = infiniteRepeatable(tween(5000, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "scale"
    )

    Canvas(modifier = Modifier.fillMaxSize().graphicsLayer { alpha = 0.07f }) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        val positions = listOf(
            Triple(0.12f, 0.15f, -15f),
            Triple(0.85f, 0.12f, 20f),
            Triple(0.08f, 0.55f, -30f),
            Triple(0.92f, 0.65f, 40f),
            Triple(0.25f, 0.90f, 15f),
            Triple(0.70f, 0.95f, -10f)
        )

        positions.forEachIndexed { index, (relX, relY, rotation) ->
            val isEven = index % 2 == 0
            val offset = if (isEven) moveAmount else -moveAmount
            val scale = if (isEven) scaleFactor else (2f - scaleFactor)
            val sizePx = (55.dp).toPx() * scale

            rotate(degrees = rotation, pivot = Offset(canvasWidth * relX, canvasHeight * relY)) {
                translate(
                    left = (canvasWidth * relX - sizePx / 2) + offset,
                    top = (canvasHeight * relY - sizePx / 2) + (offset * 0.3f)
                ) {
                    with(pawPainter) {
                        draw(size = Size(sizePx, sizePx), colorFilter = ColorFilter.tint(color))
                    }
                }
            }
        }
    }
}
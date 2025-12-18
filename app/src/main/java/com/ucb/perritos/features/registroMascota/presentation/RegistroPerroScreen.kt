package com.ucb.perritos.features.registroMascota.presentation

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Pets
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.ucb.perritos.R
import com.ucb.perritos.features.registroMascota.domain.model.PerroModel
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
    var avatarUri by remember { mutableStateOf<Uri?>(null) }

    val state by vm.state.collectAsState()
    val context = LocalContext.current


    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> avatarUri = uri }


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


    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }


    RegistroPerroScreenContent(
        nombrePerro = nombrePerro,
        raza = raza,
        edad = edad,
        descripcion = descripcion,
        avatarUri = avatarUri,
        isLoading = state is RegistroPerroViewModel.RegistrarPerroStateUI.Loading,
        visible = visible,
        onNombreChange = { nombrePerro = it },
        onRazaChange = { raza = it },
        onEdadChange = { edad = it },
        onDescripcionChange = { descripcion = it },
        onPhotoClick = { pickImageLauncher.launch("image/*") },
        onRegistrarClick = {
            vm.registrarPerro(
                PerroModel(
                    nombre_perro = nombrePerro,
                    raza = raza,
                    edad = edad.toIntOrNull() ?: 0,
                    descripcion = descripcion,
                    id_usuario = "",
                    foto_perro = null,
                ),
                avatarUri = avatarUri
            )
        }
    )
}


@Composable
fun RegistroPerroScreenContent(
    nombrePerro: String,
    raza: String,
    edad: String,
    descripcion: String,
    avatarUri: Uri?,
    isLoading: Boolean,
    visible: Boolean = true,
    onNombreChange: (String) -> Unit,
    onRazaChange: (String) -> Unit,
    onEdadChange: (String) -> Unit,
    onDescripcionChange: (String) -> Unit,
    onPhotoClick: () -> Unit,
    onRegistrarClick: () -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize(), color = WhiteBackground) {
        Box(modifier = Modifier.fillMaxSize()) {


            SubtlePawsBackground(color = OrangePrimary)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                EntradaAnimada(visible = visible, delay = 100) {
                    PetPhotoSection(
                        avatarUri = avatarUri,
                        onClickChangePhoto = onPhotoClick,
                        tag = "tagFotoMascota"
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))


                EntradaAnimada(visible = visible, delay = 300) {
                    Text(
                        text = stringResource(id = R.string.Registrar_perro),
                        color = OrangePrimary,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))


                EntradaAnimada(visible = visible, delay = 500) {
                    MascotaCustomTextField(
                        label = stringResource(id = R.string.nombre_perro),
                        value = nombrePerro,
                        onValueChange = onNombreChange,
                        tag = "tagNombrePerro"
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                EntradaAnimada(visible = visible, delay = 650) {
                    MascotaCustomTextField(
                        label = stringResource(id = R.string.raza),
                        value = raza,
                        onValueChange = onRazaChange,
                        tag = "tagRaza"
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                EntradaAnimada(visible = visible, delay = 800) {
                    MascotaCustomTextField(
                        label = stringResource(id = R.string.edad),
                        value = edad,
                        onValueChange = onEdadChange,
                        keyboardType = KeyboardType.Number,
                        tag = "tagEdad"
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                EntradaAnimada(visible = visible, delay = 950) {
                    MascotaCustomTextField(
                        label = stringResource(id = R.string.descripcion),
                        value = descripcion,
                        onValueChange = onDescripcionChange,
                        singleLine = false,
                        maxLines = 3,
                        tag = "tagDescripcion"
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))


                EntradaAnimada(visible = visible, delay = 1100) {
                    Button(
                        onClick = onRegistrarClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                            .testTag("tagBotonRegistrar")
                            .graphicsLayer {
                                shadowElevation = 8f
                                shape = RoundedCornerShape(27.dp)
                                clip = true
                            },
                        shape = RoundedCornerShape(27.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = OrangePrimary,
                            contentColor = Color.White
                        ),
                        enabled = !isLoading
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                        } else {
                            Text(text = stringResource(id = R.string.registrarme), fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}


@Composable
fun PetPhotoSection(avatarUri: Uri?, onClickChangePhoto: () -> Unit, tag: String) {

    val infiniteTransition = rememberInfiniteTransition(label = "photo_float")
    val floatY by infiniteTransition.animateFloat(
        initialValue = -8f, targetValue = 8f,
        animationSpec = infiniteRepeatable(tween(2000, easing = FastOutSlowInEasing), RepeatMode.Reverse), label = "y"
    )
    val rotationWiggle by infiniteTransition.animateFloat(
        initialValue = -2f, targetValue = 2f,
        animationSpec = infiniteRepeatable(tween(2500, easing = FastOutSlowInEasing), RepeatMode.Reverse), label = "wiggle"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(120.dp)
            .testTag(tag)
            .graphicsLayer {
                translationY = floatY
                rotationZ = rotationWiggle
            }

            .clickable(onClick = onClickChangePhoto)
    ) {
        Box(contentAlignment = Alignment.BottomEnd) {
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .border(3.dp, OrangePrimary, CircleShape)
                    .padding(4.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFF0F0F0)),
                contentAlignment = Alignment.Center
            ) {
                if (avatarUri != null) {
                    AsyncImage(
                        model = avatarUri,
                        contentDescription = stringResource(id = R.string.foto_mascota),
                        modifier = Modifier.fillMaxSize().clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {

                    Icon(
                        imageVector = Icons.Default.Pets,
                        contentDescription = stringResource(id = R.string.foto_mascota),
                        tint = TextBlueGray.copy(alpha = 0.5f),
                        modifier = Modifier.size(60.dp)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .size(36.dp)
                    .offset(x = 6.dp, y = 6.dp)
                    .background(WhiteBackground, CircleShape)
                    .border(2.dp, OrangePrimary, CircleShape)
                    .padding(6.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowUpward,
                    contentDescription = stringResource(id = R.string.subir_foto),
                    tint = OrangePrimary,
                    modifier = Modifier.size(20.dp)
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
    maxLines: Int = 1,
    tag: String
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
            modifier = Modifier
                .fillMaxWidth()
                .testTag(tag),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = OrangePrimary,
                unfocusedBorderColor = Color.LightGray,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color(0xFFF9F9F9),
                cursorColor = OrangePrimary
            ),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            singleLine = singleLine,
            maxLines = maxLines
        )
    }
}



@Composable
private fun EntradaAnimada(visible: Boolean, delay: Int, content: @Composable () -> Unit) {
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
    val infiniteTransition = rememberInfiniteTransition(label = "bg_paws")

    val moveX by infiniteTransition.animateFloat(
        initialValue = -20f, targetValue = 20f,
        animationSpec = infiniteRepeatable(tween(15000, easing = LinearEasing), RepeatMode.Reverse), label = "x"
    )
    val scaleFactor by infiniteTransition.animateFloat(
        initialValue = 0.9f, targetValue = 1.1f,
        animationSpec = infiniteRepeatable(tween(4000, easing = FastOutSlowInEasing), RepeatMode.Reverse), label = "scale"
    )

    Canvas(modifier = Modifier.fillMaxSize().graphicsLayer { alpha = 0.06f }) {
        val pawSize = 50.dp.toPx()
        val positions = listOf(Offset(0.1f, 0.15f), Offset(0.85f, 0.1f), Offset(0.05f, 0.45f), Offset(0.9f, 0.75f), Offset(0.3f, 0.9f))

        positions.forEachIndexed { i, pos ->
            val dir = if (i % 2 == 0) 1f else -1f
            val finalSize = pawSize * (if (i % 2 == 0) scaleFactor else (2f - scaleFactor))
            translate(size.width * pos.x + (moveX * dir), size.height * pos.y) {
                with(pawPainter) { draw(size = Size(finalSize, finalSize), colorFilter = ColorFilter.tint(color)) }
            }
        }
    }
}
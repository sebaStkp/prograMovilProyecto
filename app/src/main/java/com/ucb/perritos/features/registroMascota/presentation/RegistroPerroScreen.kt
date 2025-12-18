package com.ucb.perritos.features.registroMascota.presentation

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
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

// 1. PANTALLA INTELIGENTE (LOGICA)
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

    // Launcher para galería
    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> avatarUri = uri }

    // Efectos (Toast y Navegación)
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

    // LLAMADA A LA VISTA PURA
    RegistroPerroScreenContent(
        nombrePerro = nombrePerro,
        raza = raza,
        edad = edad,
        descripcion = descripcion,
        avatarUri = avatarUri,
        isLoading = state is RegistroPerroViewModel.RegistrarPerroStateUI.Loading,
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

// 2. PANTALLA PURA (PARA TESTING) - AQUÍ AGREGAMOS LOS TAGS
@Composable
fun RegistroPerroScreenContent(
    nombrePerro: String,
    raza: String,
    edad: String,
    descripcion: String,
    avatarUri: Uri?,
    isLoading: Boolean,
    onNombreChange: (String) -> Unit,
    onRazaChange: (String) -> Unit,
    onEdadChange: (String) -> Unit,
    onDescripcionChange: (String) -> Unit,
    onPhotoClick: () -> Unit,
    onRegistrarClick: () -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize(), color = WhiteBackground) {
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

                PetPhotoSection(
                    avatarUri = avatarUri,
                    onClickChangePhoto = onPhotoClick,
                    tag = "tagFotoMascota" // Tag para la foto
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(id = R.string.Registrar_perro),
                    color = OrangePrimary,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                MascotaCustomTextField(
                    label = stringResource(id = R.string.nombre_perro),
                    value = nombrePerro,
                    onValueChange = onNombreChange,
                    tag = "tagNombrePerro" // Tag Nombre
                )

                Spacer(modifier = Modifier.height(16.dp))

                MascotaCustomTextField(
                    label = stringResource(id = R.string.raza),
                    value = raza,
                    onValueChange = onRazaChange,
                    tag = "tagRaza" // Tag Raza
                )

                Spacer(modifier = Modifier.height(16.dp))

                MascotaCustomTextField(
                    label = stringResource(id = R.string.edad),
                    value = edad,
                    onValueChange = onEdadChange,
                    keyboardType = KeyboardType.Number,
                    tag = "tagEdad" // Tag Edad
                )

                Spacer(modifier = Modifier.height(16.dp))

                MascotaCustomTextField(
                    label = stringResource(id = R.string.descripcion),
                    value = descripcion,
                    onValueChange = onDescripcionChange,
                    singleLine = false,
                    maxLines = 3,
                    tag = "tagDescripcion" // Tag Descripcion
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = onRegistrarClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("tagBotonRegistrar"), // Tag Botón
                    shape = RoundedCornerShape(25.dp),
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
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

// Se agrega parámetro TAG a la sección de foto
@Composable
fun PetPhotoSection(avatarUri: Uri?, onClickChangePhoto: () -> Unit, tag: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(110.dp)
            .background(WhiteBackground, CircleShape)
            .testTag(tag) // <--- TAG AQUÍ
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
                if (avatarUri != null) {
                    AsyncImage(
                        model = avatarUri,
                        contentDescription = stringResource(id = R.string.foto_mascota),
                        modifier = Modifier.fillMaxSize().clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = stringResource(id = R.string.foto_mascota),
                        tint = TextBlueGray,
                        modifier = Modifier.size(70.dp)
                    )
                }
            }
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .offset(x = 4.dp, y = 4.dp)
                    .background(WhiteBackground, CircleShape)
                    .border(1.dp, OrangePrimary, CircleShape)
                    .padding(4.dp)
            ) {
                IconButton(onClick = onClickChangePhoto, modifier = Modifier.fillMaxSize()) {
                    Icon(imageVector = Icons.Default.ArrowUpward, contentDescription = stringResource(id = R.string.subir_foto), tint = OrangePrimary, modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}

// Se agrega parámetro TAG al TextField
@Composable
fun MascotaCustomTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    tag: String // <--- NUEVO PARAMETRO
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
            modifier = Modifier
                .fillMaxWidth()
                .testTag(tag), // <--- USAMOS EL TAG
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
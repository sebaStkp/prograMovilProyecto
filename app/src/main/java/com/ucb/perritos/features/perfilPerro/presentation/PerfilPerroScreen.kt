package com.ucb.perritos.features.perfilPerro.presentation

import android.Manifest
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import coil3.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel
import java.io.File

private val OrangePrimary = androidx.compose.ui.graphics.Color(0xFFF89A22)
private val TextBlueGray = androidx.compose.ui.graphics.Color(0xFF6A8693)
private val TextGray = androidx.compose.ui.graphics.Color(0xFF9E9E9E)

@Composable
fun PerfilPerroScreen(
    modifier: Modifier = Modifier,
    vm: PerfilPerroViewModel = koinViewModel(),
    perroId: Long = 1L
) {
    val context = LocalContext.current
    val state by vm.state.collectAsState()

    LaunchedEffect(perroId) { vm.init(perroId) }

    val photoFile = remember {
        File(context.getExternalFilesDir(null), "foto_perro_${System.currentTimeMillis()}.jpg")
    }
    val photoUri = remember {
        FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            photoFile
        )
    }
    val photoUriState = remember { mutableStateOf<Uri?>(null) }

    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            photoUriState.value = photoUri
            vm.agregarFoto(perroId, photoUri)
        }
    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) takePictureLauncher.launch(photoUri)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 24.dp, bottom = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // AVATAR GRANDE
            AsyncImage(
                model = state.perfil?.avatarUrl ?: photoUriState.value,
                contentDescription = "Avatar perro",
                modifier = Modifier
                    .size(140.dp) // <-- más grande (mockup)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.height(10.dp))

//            // NOMBRE (naranja)
//            Text(
//                text = state.perfil?.nombre ?: "—",
//                fontSize = 18.sp,
//                fontWeight = FontWeight.Bold,
//                color = OrangePrimary
//            )
//
//            // RAZA (azul/gris)
//            Text(
//                text = state.perfil?.raza ?: "",
//                fontSize = 14.sp,
//                color = TextBlueGray
//            )

            Spacer(Modifier.height(18.dp))

            // CAJA "INFORMACION DE LA MASCOTA"
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = androidx.compose.ui.graphics.Color.White),
                border = BorderStroke(2.dp, OrangePrimary)
            ) {
                Column(Modifier.padding(14.dp)) {
                    Text(
                        text = "Información de la mascota",
                        color = OrangePrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Spacer(Modifier.height(10.dp))

                    InfoRow("Nombre:", state.perfil?.nombre ?: "—")
                    InfoRow("Raza:", state.perfil?.raza ?: "—")

                    // Si todavía no tienes edad/descripcion en PerfilPerroModel,
                    // por ahora se muestra “—”. Cuando lo agreguen en el model, aquí ya sale.
                    InfoRow("Edad:", state.perfil?.edad?.toString() ?: "—")
                    InfoRow("Descripción:", state.perfil?.descripcion ?: "—")

                }
            }

            Spacer(Modifier.height(18.dp))

            // TITULO FOTOS
            Text(
                text = "FOTOS DEL PERRO",
                color = OrangePrimary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(horizontal = 24.dp)
            )

            Spacer(Modifier.height(12.dp))

            // (Por ahora placeholder del contenedor de fotos, luego metemos LazyRow / grid)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .height(140.dp),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = androidx.compose.ui.graphics.Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Aquí irán las fotos", color = TextGray)
                }
            }

            Spacer(Modifier.height(14.dp))

            // BOTÓN AÑADIR FOTO (naranja)
            Button(
                onClick = { requestPermissionLauncher.launch(Manifest.permission.CAMERA) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = OrangePrimary,
                    contentColor = androidx.compose.ui.graphics.Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text("Añadir foto", fontWeight = FontWeight.Bold)
            }

            if (state.loading) {
                Spacer(Modifier.height(16.dp))
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                    color = OrangePrimary
                )
            }
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = OrangePrimary, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
        Text(text = value, color = TextBlueGray, fontSize = 13.sp)
    }
    Spacer(Modifier.height(6.dp))
}
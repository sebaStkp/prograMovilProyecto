package com.ucb.perritos.features.perfilPerro.presentation

import android.Manifest
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import coil3.compose.AsyncImage
import com.ucb.perritos.R
import org.koin.androidx.compose.koinViewModel
import java.io.File


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Pets


private val OrangePrimary = Color(0xFFF89A22)
private val OrangeLight = Color(0xFFFFF3E0)
private val TextDark = Color(0xFF2D3436)
private val TextBlueGray = Color(0xFF6A8693)
private val TextGray = Color(0xFF9E9E9E)

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
            .background(Color(0xFFFAFAFA))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 40.dp, bottom = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            val imageModel = state.perfil?.avatarUrl ?: photoUriState.value

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(160.dp)
            ) {
                if (imageModel != null) {
                    AsyncImage(
                        model = imageModel,
                        contentDescription = stringResource(id = R.string.perfil_perro_img_cd_avatar),
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                            .border(4.dp, OrangePrimary, CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                            .background(Color(0xFFEEEEEE))
                            .border(2.dp, OrangePrimary.copy(alpha = 0.5f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Image,
                            contentDescription = "Sin foto",
                            tint = Color.Gray,
                            modifier = Modifier.size(64.dp)
                        )
                    }
                }


                Surface(
                    shape = CircleShape,
                    color = OrangePrimary,
                    shadowElevation = 4.dp,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(8.dp)
                        .size(40.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.AddAPhoto,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))


            Text(
                text = state.perfil?.nombre ?: "Nombre Desconocido",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )

            Text(
                text = state.perfil?.raza ?: "Raza no especificada",
                style = MaterialTheme.typography.bodyLarge,
                color = TextBlueGray,
                fontWeight = FontWeight.Medium
            )

            Spacer(Modifier.height(24.dp))



            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Box(
                            modifier = Modifier
                                .size(width = 4.dp, height = 20.dp)
                                .background(OrangePrimary, RoundedCornerShape(2.dp))
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = stringResource(id = R.string.perfil_perro_card_titulo_info),
                            color = TextDark,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }

                    Spacer(Modifier.height(16.dp))


                    HorizontalDivider(color = Color(0xFFF0F0F0))
                    Spacer(Modifier.height(12.dp))

                    InfoRow("${stringResource(id = R.string.perfil_perro_card_label_nombre)}", state.perfil?.nombre ?: "—")
                    InfoRow("${stringResource(id = R.string.perfil_perro_card_label_raza)}", state.perfil?.raza ?: "—")
                    InfoRow("${stringResource(id = R.string.perfil_perro_card_label_edad)}", state.perfil?.edad?.toString() ?: "—")
                    InfoRow("${stringResource(id = R.string.perfil_perro_card_label_descripcion)}", state.perfil?.descripcion ?: "—")
                }
            }

            Spacer(Modifier.height(24.dp))


            Text(
                text = stringResource(id = R.string.perfil_perro_titulo_fotos),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = TextDark,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(horizontal = 28.dp)
            )

            Spacer(Modifier.height(12.dp))



            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .height(120.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = OrangeLight.copy(alpha = 0.3f)),
                border = BorderStroke(1.dp, OrangePrimary.copy(alpha = 0.3f))
            ) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.Image,
                            contentDescription = null,
                            tint = OrangePrimary.copy(alpha = 0.6f),
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = stringResource(id = R.string.perfil_perro_placeholder_fotos),
                            color = TextBlueGray,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            Spacer(Modifier.height(24.dp))


            Button(
                onClick = { requestPermissionLauncher.launch(Manifest.permission.CAMERA) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = OrangePrimary,
                    contentColor = Color.White
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 6.dp,
                    pressedElevation = 2.dp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .height(54.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AddAPhoto,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(id = R.string.Aniadir_Foto),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = TextGray,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
        )
        Text(
            text = value,
            color = TextDark,
            fontWeight = FontWeight.SemiBold,
            fontSize = 15.sp,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}
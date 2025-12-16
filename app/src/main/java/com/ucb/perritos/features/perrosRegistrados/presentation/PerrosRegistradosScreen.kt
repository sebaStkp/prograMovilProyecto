package com.ucb.perritos.features.perrosRegistrados.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ucb.perritos.R
import com.ucb.perritos.features.registroMascota.data.dto.PerroDto
import org.koin.androidx.compose.koinViewModel
import coil3.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale


private val OrangePrimary = Color(0xFFF89A22)
private val TextBlueGray = Color(0xFF6A8693)
private val TextGray = Color(0xFF9E9E9E)

@Composable
fun PerrosRegistradosScreen(
    vm: PerrosRegistradosViewModel = koinViewModel(),
    irRegistroPerro: () -> Unit,
    irDetallePerro: (Long) -> Unit
) {
    val state by vm.state.collectAsState()

    LaunchedEffect(Unit) {
        vm.cargarMisPerros()
        vm.navigationEvent.collect { _ -> }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 16.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(20.dp))


            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Pets,
                    contentDescription = null,
                    tint = OrangePrimary,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = stringResource(id = R.string.perros_registrados_titulo_mis_perros),
                        color = OrangePrimary,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    if (state is PerrosRegistradosViewModel.PerrosRegistradosStateUI.Success) {
                        val count = (state as PerrosRegistradosViewModel.PerrosRegistradosStateUI.Success).perros.size
                        Text(
                            text = stringResource(id = R.string.perros_registrados_txt_conteo_perros, count),
                            color = TextGray,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))


            when (val st = state) {
                is PerrosRegistradosViewModel.PerrosRegistradosStateUI.Loading -> {
                    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = OrangePrimary)
                    }
                }
                is PerrosRegistradosViewModel.PerrosRegistradosStateUI.Error -> {
                    Text(
                        text = stringResource(id = R.string.perros_registrados_error_cargando),
                        color = Color.Red
                    )
                }
                is PerrosRegistradosViewModel.PerrosRegistradosStateUI.Empty -> {
                    Text(
                        text = stringResource(id = R.string.perros_registrados_txt_sin_perros),
                        color = TextGray
                    )
                }
                is PerrosRegistradosViewModel.PerrosRegistradosStateUI.Success -> {
                    LazyColumn(
                        // CAMBIO 1: Aumentamos el padding inferior de la lista (80dp -> 180dp)
                        // para que el último perro se vea completo encima del botón.
                        contentPadding = PaddingValues(bottom = 180.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(st.perros) { perro ->
                            DogCardItem(
                                perro = perro,
                                onEditarClick = { vm.onEditarPerroClicked(perro.id ?: 0) },
                                irDetallePerro = { irDetallePerro(perro.id?.toLong() ?: 0L) }
                            )
                        }
                    }
                }
                else -> {}
            }
        }


        Button(
            onClick = { irRegistroPerro() },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 110.dp)
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = OrangePrimary,
                contentColor = Color.White
            )
        ) {
            Text(
                text = stringResource(id = R.string.perros_registrados_btn_registrar_nuevo),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun DogCardItem(perro: PerroDto, onEditarClick: () -> Unit, irDetallePerro: () -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, OrangePrimary.copy(alpha = 0.5f)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {

                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    val url = perro.foto_perro
                    if (!url.isNullOrBlank()) {
                        AsyncImage(
                            model = url,
                            contentDescription = stringResource(id = R.string.perros_registrados_img_cd_avatar_perro),
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Icon(
                            painter = painterResource(id = R.drawable.logo_perrito),
                            contentDescription = null,
                            tint = Color.Unspecified,
                            modifier = Modifier.size(50.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))


                Column {
                    Text(
                        text = perro.nombre_perro,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextBlueGray
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = stringResource(id = R.string.perros_registrados_txt_raza, perro.raza),
                        color = TextGray,
                        fontSize = 12.sp
                    )
                    Text(
                        text = stringResource(id = R.string.perros_registrados_txt_edad_anios, perro.edad),
                        color = TextGray,
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))


            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(
                    onClick = irDetallePerro,
                    colors = ButtonDefaults.buttonColors(containerColor = OrangePrimary),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.weight(1f).height(36.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(stringResource(id = R.string.perros_registrados_btn_ver_detalles), fontSize = 12.sp)
                }

                OutlinedButton(
                    onClick = { onEditarClick() },
                    border = BorderStroke(1.dp, OrangePrimary),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = OrangePrimary),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.weight(1f).height(36.dp),
                    contentPadding = PaddingValues(0.dp)
                ) { Text(stringResource(id = R.string.perros_registrados_btn_editar), fontSize = 12.sp) }
            }
        }
    }
}
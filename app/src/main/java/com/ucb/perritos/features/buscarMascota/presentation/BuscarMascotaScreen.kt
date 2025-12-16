package com.ucb.perritos.features.buscarMascota.presentation

import android.Manifest
import android.preference.PreferenceManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

import com.ucb.perritos.features.buscarMascota.domain.model.BuscarMascotaModel
import org.koin.androidx.compose.koinViewModel
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker


private val OrangePrimary = Color(0xFFF89A22)
private val TextBlueGray = Color(0xFF6A8693)

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun BuscarMascotaScreen(
    vm: BuscarMascotaViewModel = koinViewModel()
) {
    val state by vm.state.collectAsState()
    val context = LocalContext.current


    LaunchedEffect(Unit) {
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context))
        Configuration.getInstance().userAgentValue = context.packageName
    }


    val locationPermissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)

    LaunchedEffect(Unit) {
        if (!locationPermissionState.status.isGranted) {
            locationPermissionState.launchPermissionRequest()
        } else {
            vm.cargarUbicacion()
        }
    }


    Scaffold(
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Text(
                text = "DONDE ESTOY ?",
                color = OrangePrimary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(vertical = 16.dp)
            )


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                when (state) {
                    is BuscarMascotaViewModel.BuscarMascotaViewModelStateUI.Init -> {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("Iniciando mapa...", color = Color.Gray)
                        }
                    }
                    is BuscarMascotaViewModel.BuscarMascotaViewModelStateUI.Loading -> {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(color = OrangePrimary)
                        }
                    }
                    is BuscarMascotaViewModel.BuscarMascotaViewModelStateUI.Error -> {
                        val error = (state as BuscarMascotaViewModel.BuscarMascotaViewModelStateUI.Error).message
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("Error: $error", color = Color.Red)
                        }
                    }
                    is BuscarMascotaViewModel.BuscarMascotaViewModelStateUI.Success -> {
                        val ubicacion = (state as BuscarMascotaViewModel.BuscarMascotaViewModelStateUI.Success).ubicacion


                        MapaOSMContent(ubicacion)
                    }
                }
            }


            if (state is BuscarMascotaViewModel.BuscarMascotaViewModelStateUI.Success) {
                val ubicacion = (state as BuscarMascotaViewModel.BuscarMascotaViewModelStateUI.Success).ubicacion
                InfoSection(ubicacion.direccion)
            } else {

                InfoSection("Cargando...")
            }
        }
    }
}


@Composable
fun MapaOSMContent(ubicacion: BuscarMascotaModel) {

    val geoPoint = remember(ubicacion) { GeoPoint(ubicacion.latitud, ubicacion.longitud) }

    AndroidView(
        factory = { ctx ->
            MapView(ctx).apply {
                setTileSource(TileSourceFactory.MAPNIK)
                setMultiTouchControls(true)
                controller.setZoom(18.0)
                controller.setCenter(geoPoint)


                val marker = Marker(this)
                marker.position = geoPoint
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                marker.title = "Mi Ubicación"
                marker.snippet = ubicacion.direccion
                overlays.add(marker)
            }
        },
        modifier = Modifier.fillMaxSize(),
        update = { mapView ->
            mapView.controller.setCenter(geoPoint)

            mapView.overlays.clear()
            val marker = Marker(mapView)
            marker.position = geoPoint
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            marker.title = "Mi Ubicación"
            marker.snippet = ubicacion.direccion
            mapView.overlays.add(marker)
            mapView.invalidate()
        }
    )
}


@Composable
fun InfoSection(direccion: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .padding(bottom = 40.dp)
    ) {
//        LocationInfoRow(label = "Ultima ubicacion:", value = "Av America")
//        Spacer(modifier = Modifier.height(16.dp))
//        LocationInfoRow(label = "Ubicacion actual:", value = direccion)
    }
}

@Composable
fun LocationInfoRow(label: String, value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            color = TextBlueGray,
            fontSize = 14.sp,
            modifier = Modifier.weight(1f)
        )

        Column(modifier = Modifier.weight(1.5f)) {
            Text(
                text = value,
                color = Color.Black,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Divider(color = Color.Black, thickness = 1.dp)
        }
    }
}


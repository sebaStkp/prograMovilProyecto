//package com.ucb.perritos.features.buscarMascota.presentation
//
//import android.Manifest
//import android.annotation.SuppressLint
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.google.accompanist.permissions.ExperimentalPermissionsApi
//import com.google.accompanist.permissions.rememberPermissionState
//import com.google.maps.android.compose.*
//import com.ucb.perritos.features.buscarMascota.domain.model.BuscarMascotaModel
//
//@OptIn(ExperimentalPermissionsApi::class)
//@Composable
//fun BuscarMascotaScreen(
//    viewModel: BuscarMascotaViewModel
//) {
//    val state by viewModel.state.collectAsState()
//
//    // Permiso de ubicación
//    val locationPermissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
//
//    LaunchedEffect(Unit) {
//        if (!locationPermissionState.status.isGranted) {
//            locationPermissionState.launchPermissionRequest()
//        } else {
//            viewModel.cargarUbicacion()
//        }
//    }
//
//    when (state) {
//        is BuscarMascotaViewModel.BuscarMascotaViewModelStateUI.Init -> {
//            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                Text("Preparando...")
//            }
//        }
//
//        is BuscarMascotaViewModel.BuscarMascotaViewModelStateUI.Loading -> {
//            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                CircularProgressIndicator()
//            }
//        }
//
//        is BuscarMascotaViewModel.BuscarMascotaViewModelStateUI.Error -> {
//            val error = (state as BuscarMascotaViewModel.BuscarMascotaViewModelStateUI.Error).message
//            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                Text("❌ $error", color = MaterialTheme.colorScheme.error)
//            }
//        }
//
//        is BuscarMascotaViewModel.BuscarMascotaViewModelStateUI.Success -> {
//            val ubicacion = (state as BuscarMascotaViewModel.BuscarMascotaViewModelStateUI.Success).ubicacion
//            BuscarMascotaContent(ubicacion)
//        }
//    }
//}
//
//@SuppressLint("MissingPermission")
//@Composable
//fun BuscarMascotaContent(ubicacion: BuscarMascotaModel) {
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        Text(
//            text = "¿DONDE ESTOY?",
//            style = MaterialTheme.typography.titleLarge.copy(
//                fontWeight = FontWeight.Bold,
//                color = MaterialTheme.colorScheme.primary
//            )
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // --- Mapa Google ---
//        val posicion = LatLng(ubicacion.latitud, ubicacion.longitud)
//        val cameraPosition = rememberCameraPositionState {
//            position = CameraPosition.fromLatLngZoom(posicion, 15f)
//        }
//
//        GoogleMap(
//            modifier = Modifier
//                .height(300.dp)
//                .fillMaxWidth(),
//            cameraPositionState = cameraPosition
//        ) {
//            Marker(
//                state = MarkerState(position = posicion),
//                title = "Ubicación actual",
//                snippet = ubicacion.direccion
//            )
//        }
//
//        Spacer(modifier = Modifier.height(20.dp))
//
//        // --- Información ---
//        Text(
//            text = "Última ubicación: Av América",
//            fontSize = 16.sp
//        )
//        Text(
//            text = "Ubicación actual: ${ubicacion.direccion}",
//            fontSize = 16.sp,
//            fontWeight = FontWeight.Medium
//        )
//    }
//}

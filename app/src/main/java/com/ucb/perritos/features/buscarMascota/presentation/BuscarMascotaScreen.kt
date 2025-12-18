package com.ucb.perritos.features.buscarMascota.presentation
import com.ucb.perritos.R
import android.Manifest
import android.graphics.drawable.BitmapDrawable
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
import androidx.core.content.ContextCompat
//import coil3.Bitmap
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.compose.ui.res.stringResource
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
import kotlin.math.roundToInt



//import R.drawable.ic_paw

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
            vm.cargarUbicacionInicial()
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
                text = stringResource(id = R.string.donde_estoy),
                color = OrangePrimary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            // MAPA
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                when (val st = state) {
                    is BuscarMascotaViewModel.BuscarMascotaViewModelStateUI.Init -> {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(
                                text = stringResource(id = R.string.mapa), // 👈 Mismo estilo que me mostraste
                                color = Color.Gray
                            )
                        }
                    }

                    is BuscarMascotaViewModel.BuscarMascotaViewModelStateUI.Loading -> {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(color = OrangePrimary)
                        }
                    }

                    is BuscarMascotaViewModel.BuscarMascotaViewModelStateUI.Error -> {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("Error: ${st.message}", color = Color.Red)
                        }
                    }

                    is BuscarMascotaViewModel.BuscarMascotaViewModelStateUI.Success -> {
                        MapaOSMContent(origin = st.origin, pet = st.pet)
                    }
                }
            }

            // BOTÓN (FUERA DEL WHEN ✅)
            Button(
                onClick = { vm.iniciarBusquedaMascota() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 24.dp)
                    .height(50.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = OrangePrimary)
            ) {
                Text(
                    text = stringResource(id = R.string.donde_estoy),
                    color = Color.White, fontWeight = FontWeight.Bold)
            }

            // INFO (si quieres mostrar algo abajo)
            if (state is BuscarMascotaViewModel.BuscarMascotaViewModelStateUI.Success) {
                val st = state as BuscarMascotaViewModel.BuscarMascotaViewModelStateUI.Success
                InfoSection(
                    stringResource(R.string.mascota_moviendose)) // o usa st.pet.direccion si quieres
            } else {
                InfoSection("Cargando...")
            }
        }
    }
}


@Composable
fun MapaOSMContent(origin: BuscarMascotaModel, pet: BuscarMascotaModel) {

    val originPoint = remember(origin.latitud, origin.longitud) { GeoPoint(origin.latitud, origin.longitud) }
    val petPoint = remember(pet.latitud, pet.longitud) { GeoPoint(pet.latitud, pet.longitud) }

    AndroidView(
        factory = { ctx ->
            MapView(ctx).apply {
                setTileSource(TileSourceFactory.MAPNIK)
                setMultiTouchControls(true)
                controller.setZoom(17.5)
                controller.setCenter(originPoint)

                // Crear marcadores UNA sola vez
                val markerMe = Marker(this).apply {
                    position = originPoint
                    setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    title = "Mi ubicación"
                }

                val markerPet = Marker(this).apply {
                    position = petPoint
                    setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    title = "Mascota"
                    icon = scaledDrawable(ctx, R.drawable.ic_paw, 36) // 👈 tamaño en dp
                }

                overlays.add(markerMe)
                overlays.add(markerPet)

                // Guardamos referencias para NO recrearlos
                setTag(R.id.tag_me_marker, markerMe)
                setTag(R.id.tag_pet_marker, markerPet)
            }
        },
        modifier = Modifier.fillMaxSize(),
        update = { mapView ->
            // Recuperar marcadores existentes
            val markerMe = mapView.getTag(R.id.tag_me_marker) as? Marker
            val markerPet = mapView.getTag(R.id.tag_pet_marker) as? Marker

            // Solo moverlos (NO limpiar overlays)
            markerMe?.position = originPoint
            markerPet?.position = petPoint

            // Opcional: seguir centrando en tu ubicación
            mapView.controller.setCenter(originPoint)

            mapView.invalidate()
        }
    )
}

// 👇 helper para reducir tamaño del icono en dp
private fun scaledDrawable(
    context: android.content.Context,
    drawableRes: Int,
    sizeDp: Int
): android.graphics.drawable.Drawable? {

    val drawable = ContextCompat.getDrawable(context, drawableRes) ?: return null

    val density = context.resources.displayMetrics.density
    val sizePx = (sizeDp * density).roundToInt()

    // Creamos un bitmap y dibujamos el drawable dentro
    val bitmap = Bitmap.createBitmap(sizePx, sizePx, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, sizePx, sizePx)
    drawable.draw(canvas)

    return BitmapDrawable(context.resources, bitmap)
}


//@Composable
//fun MapaOSMContent(origin: BuscarMascotaModel, pet: BuscarMascotaModel) {
//
//    val originPoint = remember(origin.latitud, origin.longitud) { GeoPoint(origin.latitud, origin.longitud) }
//    val petPoint = remember(pet.latitud, pet.longitud) { GeoPoint(pet.latitud, pet.longitud) }
//
//    AndroidView(
//        factory = { ctx ->
//            MapView(ctx).apply {
//                setTileSource(TileSourceFactory.MAPNIK)
//                setMultiTouchControls(true)
//                controller.setZoom(17.5)
//                controller.setCenter(originPoint)
//
//                // Marcador ORIGEN (yo)
//                val markerMe = Marker(this).apply {
//                    position = originPoint
//                    setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
//                    title = "Mi ubicación"
//                }
//
//                val markerPet = Marker(this).apply {
//                    position = petPoint
//                    setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
//                    title = "Mascota"
//                    icon = androidx.core.content.ContextCompat.getDrawable(
//                        ctx,
//                        R.drawable.ic_paw
//                    )
//                }
//
//                overlays.add(markerMe)
//                overlays.add(markerPet)
//            }
//        },
//        modifier = Modifier.fillMaxSize(),
//        update = { mapView ->
//            // Centro en tu ubicación
//            mapView.controller.setCenter(originPoint)
//
//            mapView.overlays.clear()
//
//            val markerMe = Marker(mapView).apply {
//                position = originPoint
//                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
//                title = "Mi ubicación"
//            }
//
//            val markerPet = Marker(mapView).apply {
//                position = petPoint
//                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
//                title = "Mascota"
//                icon = androidx.core.content.ContextCompat.getDrawable(
//                    mapView.context,
//                    R.drawable.ic_paw
//                )
//            }
//
//
//            mapView.overlays.add(markerMe)
//            mapView.overlays.add(markerPet)
//
//            mapView.invalidate()
//        }
//    )
//}


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


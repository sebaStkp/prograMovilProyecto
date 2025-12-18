package com.ucb.perritos.features.buscarMascota.presentation
import com.ucb.perritos.R
import android.Manifest
import android.graphics.drawable.BitmapDrawable
import android.preference.PreferenceManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.zIndex
import androidx.core.content.ContextCompat
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

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            // Spacer(modifier = Modifier.height(80.dp)) // <--- LÍNEA ELIMINADA

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                when (val st = state) {
                    is BuscarMascotaViewModel.BuscarMascotaViewModelStateUI.Init -> {
                        Box(
                            Modifier
                                .fillMaxSize()
                                .background(Color(0xFFF5F5F5)),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                CircularProgressIndicator(
                                    color = OrangePrimary,
                                    modifier = Modifier.size(40.dp)
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = stringResource(id = R.string.mapa), color = Color.Gray)
                            }
                        }
                    }

                    is BuscarMascotaViewModel.BuscarMascotaViewModelStateUI.Loading -> {
                        Box(
                            Modifier
                                .fillMaxSize()
                                .background(Color(0xFFF5F5F5)),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                CircularProgressIndicator(
                                    color = OrangePrimary,
                                    modifier = Modifier.size(40.dp)
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = stringResource(R.string.carga_ubi), color = Color.Gray)
                            }
                        }
                    }

                    is BuscarMascotaViewModel.BuscarMascotaViewModelStateUI.Error -> {
                        Box(
                            Modifier
                                .fillMaxSize()
                                .background(Color(0xFFF5F5F5)),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(24.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Pets,
                                    contentDescription = null,
                                    tint = Color.Red,
                                    modifier = Modifier.size(48.dp)
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = "Error: ${st.message}",
                                    color = Color.Red,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }

                    is BuscarMascotaViewModel.BuscarMascotaViewModelStateUI.Success -> {
                        MapaOSMContent(origin = st.origin, pet = st.pet)
                    }
                }
            }


            Spacer(modifier = Modifier.height(90.dp))
        }


        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .zIndex(10f)
                .shadow(8.dp),
            color = Color.White,
            shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Pets,
                    contentDescription = "Dog icon",
                    tint = OrangePrimary,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = stringResource(id = R.string.encuentra_tu),
                    color = Color.Gray,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    text = stringResource(id = R.string.perro),
                    color = OrangePrimary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }


        Button(
            onClick = { vm.iniciarBusquedaMascota() },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(horizontal = 24.dp)
                .padding(bottom = 100.dp)
                .height(56.dp)
                .zIndex(10f)
                .shadow(12.dp, CircleShape),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(containerColor = OrangePrimary),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 8.dp,
                pressedElevation = 12.dp
            )
        ) {
            Text(
                text = stringResource(id = R.string.buscar_mascota),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                letterSpacing = 0.5.sp
            )
        }
    }
}

// ... El resto de tu código (MapaOSMContent, etc.) no necesita cambios



@Composable
fun MapaOSMContent(origin: BuscarMascotaModel, pet: BuscarMascotaModel) {

    val originPoint = remember(origin.latitud, origin.longitud) {
        GeoPoint(origin.latitud, origin.longitud)
    }
    val petPoint = remember(pet.latitud, pet.longitud) {
        GeoPoint(pet.latitud, pet.longitud)
    }

    AndroidView(
        factory = { ctx ->
            MapView(ctx).apply {
                setTileSource(TileSourceFactory.MAPNIK)
                setMultiTouchControls(true)
                controller.setZoom(17.5)
                controller.setCenter(originPoint)


                val markerMe = Marker(this).apply {
                    position = originPoint
                    setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    title = "Mi ubicación"
                }


                val markerPet = Marker(this).apply {
                    position = petPoint
                    setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    title = "Tu mascota"
                    icon = scaledDrawable(ctx, R.drawable.ic_paw, 48)
                }

                overlays.add(markerMe)
                overlays.add(markerPet)


                setTag(R.id.tag_me_marker, markerMe)
                setTag(R.id.tag_pet_marker, markerPet)
            }
        },
        modifier = Modifier.fillMaxSize(),
        update = { mapView ->

            val markerMe = mapView.getTag(R.id.tag_me_marker) as? Marker
            val markerPet = mapView.getTag(R.id.tag_pet_marker) as? Marker


            markerMe?.position = originPoint
            markerPet?.position = petPoint


            mapView.controller.setCenter(originPoint)

            mapView.invalidate()
        }
    )
}


private fun scaledDrawable(
    context: android.content.Context,
    drawableRes: Int,
    sizeDp: Int
): android.graphics.drawable.Drawable? {

    val drawable = ContextCompat.getDrawable(context, drawableRes) ?: return null

    val density = context.resources.displayMetrics.density
    val sizePx = (sizeDp * density).roundToInt()

    val bitmap = Bitmap.createBitmap(sizePx, sizePx, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, sizePx, sizePx)
    drawable.draw(canvas)

    return BitmapDrawable(context.resources, bitmap)
}
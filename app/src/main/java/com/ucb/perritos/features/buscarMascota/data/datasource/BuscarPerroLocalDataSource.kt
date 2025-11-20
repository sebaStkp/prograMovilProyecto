//package com.ucb.perritos.features.buscarMascota.data.datasource
//
//import android.content.Context
//import com.ucb.perritos.features.buscarMascota.domain.model.BuscarMascotaModel
//import com.ucb.perritos.features.registroMascota.data.database.dao.IRegistroPerroDao
//import com.google.android.gms.location.LocationServices
//import kotlinx.coroutines.tasks.await
//import android.Manifest
//import androidx.annotation.RequiresPermission
//
//import android.content.pm.PackageManager
//import androidx.core.content.ContextCompat
//
//
//class BuscarPerroLocalDataSource(
//    private val dao: IRegistroPerroDao,
//    private val context: Context
//) {
//    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
//
//    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION])
//    suspend fun obtenerUbicacionActual(): BuscarMascotaModel? {
//        return try {
//            if (!checkLocationPermission()) {
//                return null // O lanza una excepción personalizada
//            }
//            val location = fusedLocationClient.lastLocation.await()
//            location?.let {
//                BuscarMascotaModel(
//                    latitud = it.latitude,
//                    longitud = it.longitude,
//                    direccion = "Av America" // Usa Geocoder si quieres la dirección real
//                )
//            }
//        } catch (e: SecurityException) {
//            null // Maneja la excepción si el permiso no está concedido
//        } catch (e: Exception) {
//            null // Maneja otras excepciones (ej: no hay proveedor de ubicación disponible)
//        }
//    }
//
//    private fun checkLocationPermission(): Boolean {
//        return ContextCompat.checkSelfPermission(
//            context,
//            Manifest.permission.ACCESS_FINE_LOCATION
//        ) == PackageManager.PERMISSION_GRANTED ||
//                ContextCompat.checkSelfPermission(
//                    context,
//                    Manifest.permission.ACCESS_COARSE_LOCATION
//                ) == PackageManager.PERMISSION_GRANTED
//    }
//
//    fun obtenerUltimaUbicacion(): BuscarMascotaModel? {
//        // Implementa la lógica para obtener la última ubicación guardada
//        return null
//    }
//}

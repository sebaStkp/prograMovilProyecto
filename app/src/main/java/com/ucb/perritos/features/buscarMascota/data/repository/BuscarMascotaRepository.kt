//package com.ucb.perritos.features.buscarMascota.data.repository
//
//import android.Manifest
//import androidx.annotation.RequiresPermission
//import com.ucb.perritos.features.buscarMascota.data.datasource.BuscarPerroLocalDataSource
//import com.ucb.perritos.features.buscarMascota.domain.model.BuscarMascotaModel
//import com.ucb.perritos.features.buscarMascota.domain.repository.IBuscarMascotaRepository
//
//class BuscarMascotaRepository(
//    private val localDataSource: BuscarPerroLocalDataSource
//): IBuscarMascotaRepository {
//    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION])
//    override suspend fun obtenerUbicacionActual(): BuscarMascotaModel? {
//        return localDataSource.obtenerUbicacionActual()
//    }
//
//    override suspend fun obtenerUltimaUbicacion(): BuscarMascotaModel? {
//        return localDataSource.obtenerUltimaUbicacion()
//    }
//}
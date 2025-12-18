package com.ucb.perritos.features.historialPerros.domain.repository

import com.ucb.perritos.features.historialPerros.domain.model.HistorialUbicacionModel

interface IHistorialPerrosRepository {

    suspend fun guardarUbicacionEnHistorial(historial: HistorialUbicacionModel): Result<Unit>


    suspend fun obtenerHistorialPorPerro(perroId: Long): Result<List<HistorialUbicacionModel>>
}
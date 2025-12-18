package com.ucb.perritos.features.historialPerros.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class HistorialUbicacionModel(
    val id: Int? = null,
    val perro_id: Long,
    val latitud: Double,
    val longitud: Double,
    val direccion: String,
    val fecha_hora: String? = null
)
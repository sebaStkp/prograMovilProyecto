package com.ucb.perritos.features.historialPerros.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HistorialUbicacionModel(
    val id: Int? = null,
    val perro_id: Long,
    val latitud: Double,
    val longitud: Double,
    @SerialName("created_at")
    val fecha_hora: String? = null
)
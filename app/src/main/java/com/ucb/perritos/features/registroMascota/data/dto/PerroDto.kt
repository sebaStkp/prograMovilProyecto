package com.ucb.perritos.features.registroMascota.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PerroDto(
    val id: Int? = null,
    val nombre_perro: String,
    val raza: String,
    val edad: Int,
    val descripcion: String,
    @SerialName("id_usuario") val id_usuario: String
)
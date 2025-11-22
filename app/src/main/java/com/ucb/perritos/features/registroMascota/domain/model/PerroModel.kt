package com.ucb.perritos.features.registroMascota.domain.model

data class PerroModel(
    val id: Int? = null,
    val nombre_perro: String,
    val id_usuario: String,
    val raza: String,
    val edad: Int?,
    val descripcion: String?,
    val foto_perro: String?,
)

package com.ucb.perritos.features.registroMascota.domain.model

data class RegistroPerroModel(
    val nombrePerro: String? = null,
    val raza: String? = null,
    val edad: Int? = 0,
    val descripcion: String? = null
)

package com.ucb.perritos.features.perfilPerro.domain.model

data class FotoPerroModel(
    val id: Long = 0,
    val perroId: Long,
    val url: String,
    val timestamp: Long
)
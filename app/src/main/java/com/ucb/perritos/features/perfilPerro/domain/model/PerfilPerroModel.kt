package com.ucb.perritos.features.perfilPerro.domain.model

data class PerfilPerroModel(val perroId: Long, val nombre: String, val raza: String, val edad: Int?,
                            val descripcion: String?, val avatarUrl: String?)


package com.ucb.perritos.features.registroMascota.domain.model

@JvmInline
value class UsuarioId(val value: String) {
    init {
        require(value.isNotBlank()) { "El ID de usuario es obligatorio." }
    }

    override fun toString() = value
}
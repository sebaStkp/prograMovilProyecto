package com.ucb.perritos.features.registroMascota.domain.model

@JvmInline
value class Descripcion(val value: String) {
    init {
        require(value.isNotBlank()) { "La descripción no puede estar vacía." }
        require(value.length <= 250) { "La descripción es demasiado larga (máx 250 caracteres)." }
    }

    override fun toString() = value
}
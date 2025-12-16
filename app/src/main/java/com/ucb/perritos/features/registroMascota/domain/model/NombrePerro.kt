package com.ucb.perritos.features.registroMascota.domain.model

@JvmInline
value class NombrePerro(val value: String) {
    init {
        require(value.isNotBlank()) { "El nombre del perro no puede estar vacío." }
        require(value.length <= 30) { "El nombre es demasiado largo (máx 30 caracteres)." }
        require(value.all { it.isLetter() || it.isWhitespace() }) { "El nombre solo puede contener letras." }
    }

    override fun toString() = value
}
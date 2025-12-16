package com.ucb.perritos.features.registroMascota.domain.model

@JvmInline
value class Raza(val value: String) {
    init {
        require(value.isNotBlank()) { "La raza no puede estar vacía." }
        require(value.all { it.isLetter() || it.isWhitespace() }) { "La raza solo puede contener letras." }
    }

    override fun toString() = value
}
package com.ucb.perritos.features.registroUsuario.domain.model


@JvmInline
value class Email(val value: String) {

    // SOLUCIÓN: Movemos el regex al companion object
    companion object {
        private val EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$".toRegex()
    }

    init {
        require(value.isNotBlank()) { "El email no puede estar vacío." }
        // Ahora accedemos a EMAIL_REGEX desde aquí sin problema
        require(value.matches(EMAIL_REGEX)) { "El formato del email es inválido." }
    }

    override fun toString() = value
}
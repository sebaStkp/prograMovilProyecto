package com.ucb.perritos.features.login.domain.model

@JvmInline
value class Token(val value: String) {
    init {
        require(value.isNotBlank()) { "El token de sesión no puede estar vacío." }
        require(value.length > 10) { "El token parece inválido (muy corto)." }
    }

    override fun toString() = value
}
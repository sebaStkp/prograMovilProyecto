package com.ucb.perritos.features.login.domain.model

@JvmInline
value class Nickname(val value: String) {
    init {
        require(value.isNotBlank()) { "El nickname no puede estar vacío." }
        require(value.length >= 3) { "El nickname es muy corto (mínimo 3 caracteres)." }
        require(value.length <= 50) { "El nickname es demasiado largo." }
    }

    override fun toString() = value
}
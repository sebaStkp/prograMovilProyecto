package com.ucb.perritos.features.registroUsuario.domain.model

@JvmInline
value class Password(val value: String) {
    init {
        require(value.length >= 8) { "La contraseña debe tener al menos 8 caracteres." }
        require(value.any { it.isDigit() }) { "La contraseña debe contener al menos un número." }
        require(value.any { it.isUpperCase() }) { "La contraseña debe contener al menos una letra mayúscula." }
    }

    // Por seguridad, nunca imprimimos la contraseña real en logs
    override fun toString() = "*******"
}
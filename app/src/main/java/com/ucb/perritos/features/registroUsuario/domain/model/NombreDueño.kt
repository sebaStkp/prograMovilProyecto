package com.ucb.perritos.features.registroUsuario.domain.model


@JvmInline
value class NombreDueño(val value: String) {
    init {
        // 1. No debe estar vacío ni ser solo espacios
        require(value.isNotBlank()) { "El nombre no puede estar vacío." }

        // 2. Longitud máxima (por ejemplo, para base de datos)
        require(value.length <= 50) { "El nombre es demasiado largo (máx 50 caracteres)." }

        // 3. Solo debe contener letras y espacios (ni números, ni símbolos)
        require(value.all { it.isLetter() || it.isWhitespace() }) { "El nombre solo puede contener letras." }
    }

    override fun toString() = value
}
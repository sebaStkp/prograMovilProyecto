package com.ucb.perritos.features.registroMascota.domain.model

@JvmInline
value class FotoUrl(val value: String) {
    init {
        require(value.startsWith("http://") || value.startsWith("https://")) {
            "La URL debe comenzar con http o https."
        }
    }

    override fun toString() = value
}
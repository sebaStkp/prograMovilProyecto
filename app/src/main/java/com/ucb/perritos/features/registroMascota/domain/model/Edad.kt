package com.ucb.perritos.features.registroMascota.domain.model

@JvmInline
value class Edad(val value: Int) {
    init {
        require(value >= 0) { "La edad no puede ser negativa." }
        require(value < 30) { "La edad ingresada no es realista." }
    }

    override fun toString() = value.toString()
}
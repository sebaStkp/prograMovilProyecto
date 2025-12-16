package com.ucb.perritos.features.buscarMascota.domain.model

@JvmInline
value class Latitud(val value: Double) {
    init {
        require(value >= -90.0 && value <= 90.0) {
            "La latitud debe estar entre -90 y 90 grados."
        }
    }
    override fun toString() = value.toString()
}
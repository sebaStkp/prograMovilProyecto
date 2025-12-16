package com.ucb.perritos.features.buscarMascota.domain.model

@JvmInline
value class Longitud(val value: Double) {
    init {
        require(value >= -180.0 && value <= 180.0) {
            "La longitud debe estar entre -180 y 180 grados."
        }
    }
    override fun toString() = value.toString()
}
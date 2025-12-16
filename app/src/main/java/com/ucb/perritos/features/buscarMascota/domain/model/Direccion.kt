package com.ucb.perritos.features.buscarMascota.domain.model

@JvmInline
value class Direccion(val value: String) {
    init {
        require(value.isNotBlank()) { "La dirección no puede estar vacía." }
        require(value.length <= 200) { "La dirección es demasiado larga." }
    }
    override fun toString() = value
}
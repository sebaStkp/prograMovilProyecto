package com.ucb.perritos.features.buscarMascota.data.remote

//class UbicacionApiModels {
//
//}

data class UbicacionResponseDto(
    val origin: PuntoDto,
    val current: CurrentDto
)

data class PuntoDto(
    val latitud: Double,
    val longitud: Double
)

data class CurrentDto(
    val latitud: Double,
    val longitud: Double,
    val timestamp: Double,
    val dispositivo_id: String
)
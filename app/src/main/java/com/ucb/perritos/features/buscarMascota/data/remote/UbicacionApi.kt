package com.ucb.perritos.features.buscarMascota.data.remote

import retrofit2.http.GET

interface UbicacionApi {
    @GET("ubicacion_mascota")
    suspend fun getUbicacion(): UbicacionResponseDto
}
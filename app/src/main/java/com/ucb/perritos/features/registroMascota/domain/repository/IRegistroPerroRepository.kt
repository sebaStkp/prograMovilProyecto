package com.ucb.perritos.features.registroMascota.domain.repository

import com.ucb.perritos.features.registroMascota.data.dto.PerroDto
import com.ucb.perritos.features.registroMascota.domain.model.PerroModel

interface IRegistroPerroRepository {
    suspend fun registrarPerro(perro: PerroModel) : Result<PerroModel>
    suspend fun obtenerPerros(id_usuario: String): Result<List<PerroDto>>
    suspend fun editarPerro(perro: PerroModel, id_perro: Int): Result<PerroModel>
}
package com.ucb.perritos.features.registroMascota.domain.repository

import com.ucb.perritos.features.registroMascota.domain.model.RegistroPerroModel
import kotlinx.coroutines.flow.Flow

interface IRegistroPerroRepository {
    suspend fun registrarPerro(perro: RegistroPerroModel) : Result<RegistroPerroModel>
}
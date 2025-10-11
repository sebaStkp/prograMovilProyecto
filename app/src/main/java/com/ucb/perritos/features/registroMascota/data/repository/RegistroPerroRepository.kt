package com.ucb.perritos.features.registroMascota.data.repository

import com.ucb.perritos.features.registroMascota.data.datasource.RegistroPerroLocalDataSource
import com.ucb.perritos.features.registroMascota.domain.model.RegistroPerroModel
import com.ucb.perritos.features.registroMascota.domain.repository.IRegistroPerroRepository


class RegistroPerroRepository(
    private val registroPerroLocalDataSource: RegistroPerroLocalDataSource
): IRegistroPerroRepository {
    override suspend fun registrarPerro(perro: RegistroPerroModel): Result<RegistroPerroModel> {
        return registroPerroLocalDataSource.insert(perro)
    }
}
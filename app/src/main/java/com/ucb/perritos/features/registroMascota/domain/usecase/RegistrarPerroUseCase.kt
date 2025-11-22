package com.ucb.perritos.features.registroMascota.domain.usecase

import com.ucb.perritos.features.registroMascota.domain.model.PerroModel
import com.ucb.perritos.features.registroMascota.domain.repository.IRegistroPerroRepository

class RegistrarPerroUseCase(
    val repository: IRegistroPerroRepository
) {
    suspend fun invoke(perro: PerroModel): Result<PerroModel>{
        return repository.registrarPerro(perro)
    }
}
package com.ucb.perritos.features.registroMascota.domain.usecase

import com.ucb.perritos.features.registroMascota.domain.model.RegistroPerroModel
import com.ucb.perritos.features.registroMascota.domain.repository.IRegistroPerroRepository

class RegistrarPerroUseCase(
    val repository: IRegistroPerroRepository
) {
    suspend fun invoke(perro: RegistroPerroModel): Result<RegistroPerroModel>{
        return repository.registrarPerro(perro)
    }
}
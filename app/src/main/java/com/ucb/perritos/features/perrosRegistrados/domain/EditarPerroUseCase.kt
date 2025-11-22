package com.ucb.perritos.features.perrosRegistrados.domain

import com.ucb.perritos.features.registroMascota.domain.model.PerroModel
import com.ucb.perritos.features.registroMascota.domain.repository.IRegistroPerroRepository

class EditarPerroUseCase(
    var repository: IRegistroPerroRepository
) {
    suspend fun invoke(perro: PerroModel, id_perro: Int): Result<PerroModel>{
        return repository.editarPerro(perro, id_perro)
    }
}
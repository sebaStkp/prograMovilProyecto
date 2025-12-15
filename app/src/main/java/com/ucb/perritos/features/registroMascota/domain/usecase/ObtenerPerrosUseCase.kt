package com.ucb.perritos.features.registroMascota.domain.usecase

import com.ucb.perritos.features.registroMascota.data.dto.PerroDto
import com.ucb.perritos.features.registroMascota.domain.repository.IRegistroPerroRepository

class ObtenerPerrosUseCase(
    val repository: IRegistroPerroRepository
) {
    suspend fun invoke(id_usuario: String): Result<List<PerroDto>> {
        return repository.obtenerPerros(id_usuario)
    }

}
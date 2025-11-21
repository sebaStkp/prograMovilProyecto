package com.ucb.perritos.features.registroUsuario.domain.usecase

import com.ucb.perritos.features.registroUsuario.domain.model.UsuarioModel
import com.ucb.perritos.features.registroUsuario.domain.repository.IRegistroUsuarioRepository

class GetUserUseCase(
    var repository: IRegistroUsuarioRepository
) {
    suspend fun invoke(email: String): Result<UsuarioModel>{
        return repository.getUsuario(email = email)
    }
}
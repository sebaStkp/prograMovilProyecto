package com.ucb.perritos.features.registroUsuario.domain.usecase

import com.ucb.perritos.features.registroUsuario.domain.model.UsuarioModel
import com.ucb.perritos.features.registroUsuario.domain.repository.IRegistroUsuarioRepository

class RegistrarUsuarioUseCase(
    val repository: IRegistroUsuarioRepository
) {
    suspend fun invoke(usuario: UsuarioModel): Result<UsuarioModel>{
        return repository.registrarUsuario(usuario)
    }
}
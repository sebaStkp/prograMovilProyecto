package com.ucb.perritos.features.registroUsuario.domain.usecase

import com.ucb.perritos.features.registroUsuario.domain.model.UsuarioModel
import com.ucb.perritos.features.registroUsuario.domain.repository.IRegistroUsuarioRepository

class GetUsuarioActual(
var repository: IRegistroUsuarioRepository
) {
    suspend fun invoke(): Result<UsuarioModel>{
        return repository.getActualUser()
    }
}
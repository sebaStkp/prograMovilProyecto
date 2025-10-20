package com.ucb.perritos.features.registroUsuario.domain.repository

import com.ucb.perritos.features.registroMascota.domain.model.RegistroPerroModel
import com.ucb.perritos.features.registroUsuario.domain.model.UsuarioModel

interface IRegistroUsuarioRepository {
    suspend fun registrarUsuario(usuario: UsuarioModel) : Result<UsuarioModel>
}
package com.ucb.perritos.features.registroUsuario.data.repository

import com.ucb.perritos.features.login.domain.model.LoginModel
import com.ucb.perritos.features.registroMascota.data.datasource.RegistroPerroLocalDataSource
import com.ucb.perritos.features.registroMascota.domain.model.RegistroPerroModel
import com.ucb.perritos.features.registroMascota.domain.repository.IRegistroPerroRepository
import com.ucb.perritos.features.registroUsuario.data.datasource.RegistroUsuarioLocalDataSource
import com.ucb.perritos.features.registroUsuario.domain.model.UsuarioModel
import com.ucb.perritos.features.registroUsuario.domain.repository.IRegistroUsuarioRepository

class RegistroUsuarioRepository(
    private val registroUsuarioLocalDataSource: RegistroUsuarioLocalDataSource
): IRegistroUsuarioRepository {
    override suspend fun registrarUsuario(usuario: UsuarioModel): Result<UsuarioModel> {
        return registroUsuarioLocalDataSource.insert(usuario)
    }

    override suspend fun getUsuario(email: String): Result<UsuarioModel> {
        return registroUsuarioLocalDataSource.getUser(email = email)
    }

}
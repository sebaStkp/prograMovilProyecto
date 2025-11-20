package com.ucb.perritos.features.login.domain.repository

import com.ucb.perritos.features.login.domain.model.LoginModel
import com.ucb.perritos.features.registroUsuario.domain.model.UsuarioModel

interface ILoginRepository {
    suspend fun getToken(nickname: String): Result<LoginModel>
    suspend fun setToken(nickname: String): Result<LoginModel>

}
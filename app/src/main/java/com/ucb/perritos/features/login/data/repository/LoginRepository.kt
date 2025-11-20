package com.ucb.perritos.features.login.data.repository



import com.ucb.perritos.features.login.domain.repository.ILoginRepository

import com.ucb.perritos.features.login.data.datasource.LoginDataStore
import com.ucb.perritos.features.login.domain.model.LoginModel
import com.ucb.perritos.features.registroUsuario.domain.model.UsuarioModel


class LoginRepository(
    private val loginDataStore: LoginDataStore
) : ILoginRepository {

    override suspend fun getToken(nickname: String): Result<LoginModel> {
        return try {

            val fakeToken = "token_${nickname}_123"
            val user = LoginModel(nickname = nickname, token = fakeToken)

            loginDataStore.saveUserName(nickname)
//            loginDataStore.saveToken(fakeToken)

            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun setToken(nickname: String): Result<LoginModel> {
        return try {

            val fakeToken = "token_${nickname}_123"
            val user = LoginModel(nickname = nickname, token = fakeToken)

            loginDataStore.saveUserName(nickname)

            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }



}

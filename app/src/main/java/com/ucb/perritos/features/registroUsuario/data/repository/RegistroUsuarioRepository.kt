package com.ucb.perritos.features.registroUsuario.data.repository

import com.ucb.perritos.features.registroUsuario.data.datasource.RegistroUsuarioLocalDataSource
import com.ucb.perritos.features.registroUsuario.data.datasource.RegistroUsuarioRemoteDataSource
import com.ucb.perritos.features.registroUsuario.domain.model.UsuarioModel
import com.ucb.perritos.features.registroUsuario.domain.repository.IRegistroUsuarioRepository
import io.github.jan.supabase.SupabaseClient // Importante
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from // Importante para .from("perfiles")
class RegistroUsuarioRepository(
    private val registroUsuarioLocalDataSource: RegistroUsuarioLocalDataSource,
    private val remoteDataSource: RegistroUsuarioRemoteDataSource
): IRegistroUsuarioRepository {
    override suspend fun registrarUsuario(usuario: UsuarioModel): Result<UsuarioModel> {
        return registroUsuarioLocalDataSource.insert(usuario)
    }

    override suspend fun getUsuario(email: String): Result<UsuarioModel> {
        return registroUsuarioLocalDataSource.getUser(email = email)
    }

    override suspend fun getActualUser(): Result<UsuarioModel> {
        return registroUsuarioLocalDataSource.getUsuarioActual()
    }

    override suspend fun actualizarTokenFCM(token: String) {
        remoteDataSource.actualizarTokenFCM(token)
    }

}
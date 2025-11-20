package com.ucb.perritos.features.registroUsuario.data.datasource

import com.ucb.perritos.features.registroUsuario.data.database.dao.IRegistroUsuarioDao
import com.ucb.perritos.features.registroUsuario.data.mapper.toEntity
import com.ucb.perritos.features.registroUsuario.data.mapper.toModel
import com.ucb.perritos.features.registroUsuario.domain.model.UsuarioModel

class RegistroUsuarioLocalDataSource(
    val dao: IRegistroUsuarioDao
) {
    suspend fun insert(usuario: UsuarioModel): Result<UsuarioModel> {
        return try {
            dao.insert(usuario.toEntity())
            Result.success(usuario)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    suspend fun getUser(email: String): Result<UsuarioModel> {
        return try {
            val entity = dao.getUsuarioPorEmail(email)
            if (entity != null) {
                Result.success(entity.toModel())
            } else {
                Result.failure(Exception("Usuario no encontrado"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
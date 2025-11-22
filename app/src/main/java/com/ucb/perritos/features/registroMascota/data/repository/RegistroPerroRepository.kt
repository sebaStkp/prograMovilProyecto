package com.ucb.perritos.features.registroMascota.data.repository

import com.ucb.perritos.features.registroMascota.data.datasource.PerroLocalDataSource
import com.ucb.perritos.features.registroMascota.data.datasource.PerroRemoteSupabase
import com.ucb.perritos.features.registroMascota.data.dto.PerroDto
import com.ucb.perritos.features.registroMascota.domain.model.PerroModel
import com.ucb.perritos.features.registroMascota.domain.repository.IRegistroPerroRepository
import kotlinx.coroutines.flow.onEach


class RegistroPerroRepository(
    private val registroPerroLocalDataSource: PerroLocalDataSource,
    private val registroPerroSupabase: PerroRemoteSupabase
): IRegistroPerroRepository {
    override suspend fun registrarPerro(perro: PerroModel): Result<PerroModel> {
        return try {
            registroPerroSupabase.registrarEnSupabase(perro)
            registroPerroLocalDataSource.insert(perro)
            Result.success(perro)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun obtenerPerros(id_usuario: String): Result<List<PerroDto>> {
        return try {
            val perritos = registroPerroSupabase.getAllPerros(id_usuario)
            Result.success(perritos)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    override suspend fun editarPerro(perro: PerroModel, id_perro: Int): Result<PerroModel> {
        val resultRemote = registroPerroSupabase.editarPerro(perro, id_perro)

        resultRemote.onSuccess {
            try {
                registroPerroLocalDataSource.editarPerro(it, id_perro)
            } catch (e: Exception) {
                println("Advertencia: Se actualizó en nube pero falló en local: ${e.message}")
            }
        }

        return resultRemote
    }
}
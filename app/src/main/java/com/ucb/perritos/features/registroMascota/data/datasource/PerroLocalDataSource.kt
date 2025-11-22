package com.ucb.perritos.features.registroMascota.data.datasource

import com.ucb.perritos.features.registroMascota.data.database.dao.IRegistroPerroDao
import com.ucb.perritos.features.registroMascota.data.mapper.toEntity
import com.ucb.perritos.features.registroMascota.domain.model.PerroModel

class PerroLocalDataSource(
    val dao: IRegistroPerroDao
) {
    suspend fun insert(perro: PerroModel): Result<PerroModel> {
        return try {
            dao.insert(perro.toEntity())
            Result.success(perro)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun editarPerro(perro: PerroModel, id_perro: Int): Result<PerroModel> {
        return try {
            val entity = perro.toEntity()

            entity.id = id_perro
            dao.update(entity)
            Result.success(perro)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
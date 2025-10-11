package com.ucb.perritos.features.registroMascota.data.datasource

import com.ucb.perritos.features.registroMascota.data.database.dao.IRegistroPerroDao
import com.ucb.perritos.features.registroMascota.data.database.entity.RegistroPerroEntity
import com.ucb.perritos.features.registroMascota.data.mapper.toEntity
import com.ucb.perritos.features.registroMascota.domain.model.RegistroPerroModel

class RegistroPerroLocalDataSource(
    val dao: IRegistroPerroDao
) {
    suspend fun insert(perro: RegistroPerroModel): Result<RegistroPerroModel> {
        return try {
            dao.insert(perro.toEntity())
            Result.success(perro)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
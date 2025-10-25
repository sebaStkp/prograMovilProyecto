package com.ucb.perritos.features.perfilPerro.data.repository

import com.ucb.perritos.features.perfilPerro.data.database.entity.PerfilPerroEntity
import com.ucb.perritos.features.perfilPerro.data.datasource.PerfilPerroLocalDataSource
import com.ucb.perritos.features.perfilPerro.data.mapper.PerfilPerroMapper
import com.ucb.perritos.features.perfilPerro.domain.model.PerfilPerroModel
import com.ucb.perritos.features.perfilPerro.domain.repository.IPerfilPerroRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PerfilPerroRepository(
    private val local: PerfilPerroLocalDataSource
):IPerfilPerroRepository {
    override fun observePerfil(perrdId: Long): Flow<PerfilPerroModel?> =
        local.observePerfil(perrdId).map { PerfilPerroMapper.toDomain(it) }

    override suspend fun setPerfilActual(
        perroId: Long,
        nombre: String,
        raza: String,
        avatarUrl: String?
    ) {
        local.upsertPerfil(
            PerfilPerroEntity(
                perroId = perroId,
                nombre = nombre,
                raza = raza,
                avatarUrl = avatarUrl
            )
        )
    }
}
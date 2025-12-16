package com.ucb.perritos.features.perfilPerro.data.repository

import com.ucb.perritos.features.perfilPerro.data.database.entity.PerfilPerroEntity
import com.ucb.perritos.features.perfilPerro.data.datasource.PerfilPerroLocalDataSource
import com.ucb.perritos.features.perfilPerro.data.datasource.PerfilPerroRemoteSupabase
import com.ucb.perritos.features.perfilPerro.data.mapper.PerfilPerroMapper
import com.ucb.perritos.features.perfilPerro.domain.model.PerfilPerroModel
import com.ucb.perritos.features.perfilPerro.domain.repository.IPerfilPerroRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PerfilPerroRepository(
    private val local: PerfilPerroLocalDataSource,
    private val remote: PerfilPerroRemoteSupabase
):IPerfilPerroRepository {
    override fun observePerfil(perroId: Long) =
        local.observePerfil(perroId).map { PerfilPerroMapper.toDomain(it) }

    override suspend fun syncPerfilDesdeSupabase(perroId: Long) {
        val perro = remote.getPerroById(perroId)

        local.upsertPerfil(
            PerfilPerroEntity(
                perroId = perroId,
                nombre = perro.nombre_perro,
                raza = perro.raza,
                avatarUrl = perro.foto_perro
            )
        )
    }

    override suspend fun setPerfilActual(perroId: Long, nombre: String, raza: String, avatarUrl: String?) {
        local.upsertPerfil(PerfilPerroEntity(perroId, nombre, raza, avatarUrl))
    }

//    override fun observePerfil(perrdId: Long): Flow<PerfilPerroModel?> =
//        local.observePerfil(perrdId).map { PerfilPerroMapper.toDomain(it) }
//
//    override suspend fun setPerfilActual(
//        perroId: Long,
//        nombre: String,
//        raza: String,
//        avatarUrl: String?
//    ) {
//        local.upsertPerfil(
//            PerfilPerroEntity(
//                perroId = perroId,
//                nombre = nombre,
//                raza = raza,
//                avatarUrl = avatarUrl
//            )
//        )
//    }
}
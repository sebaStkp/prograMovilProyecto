package com.ucb.perritos.features.perfilPerro.data.datasource

import com.ucb.perritos.features.perfilPerro.data.database.dao.IPerfilPerroDao
import com.ucb.perritos.features.perfilPerro.data.database.entity.PerfilPerroEntity

class PerfilPerroLocalDataSource(private val dao: IPerfilPerroDao) {
    fun observePerfil(perroId: Long) = dao.observePerfil(perroId)
    suspend fun upsertPerfil(perfil: PerfilPerroEntity) = dao.upsertPerfil(perfil)
}
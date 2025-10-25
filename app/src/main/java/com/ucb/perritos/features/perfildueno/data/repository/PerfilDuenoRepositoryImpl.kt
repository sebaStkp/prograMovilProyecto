package com.ucb.perritos.features.perfildueno.data.repository

import com.ucb.perritos.features.perfildueno.data.datasource.PerfilDuenoLocalDataSource
import com.ucb.perritos.features.perfildueno.data.mapper.PerfilDuenoMapper
import com.ucb.perritos.features.perfildueno.domain.model.PerfilDuenoModel
import com.ucb.perritos.features.perfildueno.domain.repository.IPerfilDuenoRepository

class PerfilDuenoRepositoryImpl(
    private val local: PerfilDuenoLocalDataSource,
    private val mapper: PerfilDuenoMapper
) : IPerfilDuenoRepository {

    override suspend fun obtenerPerfil(): PerfilDuenoModel? {
        return try {
            val entity = local.obtenerPerfil()
            mapper.entityToModel(entity)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun actualizarPerfil(perfil: PerfilDuenoModel) {
        // Implementación: guarda/actualiza el perfil localmente
        try {
            local.guardarPerfil(mapper.modelToEntity(perfil))
        } catch (e: Exception) {
            // Ignorar por ahora o propagar si se desea
        }
    }

    override suspend fun eliminarCuenta() {
        try {
            local.eliminarPerfil()
        } catch (_: Exception) {
            // ignore
        }
    }
}

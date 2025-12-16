package com.ucb.perritos.features.perfilPerro.domain.repository

import com.ucb.perritos.features.perfilPerro.domain.model.PerfilPerroModel
import kotlinx.coroutines.flow.Flow

interface IPerfilPerroRepository {
    fun observePerfil(perroId: Long): Flow<PerfilPerroModel?>

    suspend fun setPerfilActual(
        perroId: Long,
        nombre: String,
        raza: String,
        avatarUrl: String?
    )
    suspend fun syncPerfilDesdeSupabase(perroId: Long)
}
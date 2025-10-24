package com.ucb.perritos.features.perfilPerro.domain.repository

import com.ucb.perritos.features.perfilPerro.domain.model.PerfilPerroModel
import kotlinx.coroutines.flow.Flow

interface IPerfilPerroRepository {
    fun observePerfil(perroId: Long): Flow<PerfilPerroModel?>
}
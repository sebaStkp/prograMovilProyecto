package com.ucb.perritos.features.perfilPerro.domain.repository

import com.ucb.perritos.features.perfilPerro.domain.model.FotoPerroModel
import kotlinx.coroutines.flow.Flow

interface IFotoPerroRepository {
    suspend fun agregarFoto(model: FotoPerroModel)
    //suspend fun eliminarFoto(fotoId: Long)
    fun observarFotos(perroId: Long): Flow<List<FotoPerroModel>>
}
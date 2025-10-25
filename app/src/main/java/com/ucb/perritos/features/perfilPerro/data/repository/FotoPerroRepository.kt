package com.ucb.perritos.features.perfilPerro.data.repository

import com.ucb.perritos.features.perfilPerro.data.datasource.FotoPerroLocalDataSource
import com.ucb.perritos.features.perfilPerro.data.mapper.FotoPerroMapper
import com.ucb.perritos.features.perfilPerro.domain.model.FotoPerroModel
import com.ucb.perritos.features.perfilPerro.domain.repository.IFotoPerroRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FotoPerroRepository(
    private val local: FotoPerroLocalDataSource
) : IFotoPerroRepository {

    override suspend fun agregarFoto(model: FotoPerroModel) {
        local.agregarFoto(FotoPerroMapper.toEntity(model))
    }

//    override suspend fun eliminarFoto(fotoId: Long) {
//        local.eliminarFoto(fotoId)
//    }

    override fun observarFotos(perroId: Long): Flow<List<FotoPerroModel>> {
        return local.observarFotos(perroId).map { lista ->
            lista.map { FotoPerroMapper.toDomain(it) }
        }
    }
}
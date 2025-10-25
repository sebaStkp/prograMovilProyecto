package com.ucb.perritos.features.perfilPerro.data.datasource

import com.ucb.perritos.features.perfilPerro.data.database.dao.IFotoPerroDao
import com.ucb.perritos.features.perfilPerro.data.database.entity.FotoPerroEntity
import kotlinx.coroutines.flow.Flow

class FotoPerroLocalDataSource(
    private val dao: IFotoPerroDao
) {
    suspend fun agregarFoto(foto: FotoPerroEntity) {
        dao.insertarFoto(foto)
    }

//    suspend fun eliminarFoto(fotoId: Long) {
//        dao.eliminarFotoPorId(fotoId)
//    }

    fun observarFotos(perroId: Long): Flow<List<FotoPerroEntity>> {
        return dao.observarFotosDePerro(perroId)
    }
}
package com.ucb.perritos.features.perfildueno.data.datasource

import com.ucb.perritos.features.perfildueno.data.database.IPerfilDuenoDao
import com.ucb.perritos.features.perfildueno.data.database.PerfilDuenoEntity

class PerfilDuenoLocalDataSource(private val dao: IPerfilDuenoDao) {

    suspend fun obtenerPerfil(): PerfilDuenoEntity? = dao.obtenerPerfil()

    suspend fun guardarPerfil(entity: PerfilDuenoEntity) = dao.insertarPerfil(entity)

    suspend fun eliminarPerfil() = dao.eliminarPerfil()
}

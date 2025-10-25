package com.ucb.perritos.features.perfildueno.di

import android.content.Context
import com.ucb.perritos.appRoomDataBase.AppRoomDataBase
import com.ucb.perritos.features.perfildueno.data.database.InMemoryPerfilDuenoDao
import com.ucb.perritos.features.perfildueno.data.datasource.PerfilDuenoLocalDataSource
import com.ucb.perritos.features.perfildueno.data.mapper.PerfilDuenoMapper
import com.ucb.perritos.features.perfildueno.data.repository.PerfilDuenoRepository
import com.ucb.perritos.features.perfildueno.domain.usecase.ObtenerPerfilDuenoUseCase
import com.ucb.perritos.features.perfildueno.domain.usecase.EliminarCuentaUseCase

/**
 * Factory simple que devuelve objetos del feature. Si hay una BD disponible, utiliza el DAO de Room;
 * en caso contrario, usa una implementación en memoria.
 */
object PerfilDuenoModule {

    fun provideRepository(context: Context?): PerfilDuenoRepository {
        val dao = if (context != null) {
            try {
                val db = AppRoomDataBase.getDatabase(context)
                // Si la DB no expone perfilDuenoDao (por alguna razón), caemos al InMemory
                db.perfilDuenoDao()
            } catch (e: Exception) {
                InMemoryPerfilDuenoDao()
            }
        } else {
            InMemoryPerfilDuenoDao()
        }

        val local = PerfilDuenoLocalDataSource(dao)
        val mapper = PerfilDuenoMapper()
        return PerfilDuenoRepository(local, mapper)
    }

    fun provideObtenerPerfilUseCase(context: Context?) = ObtenerPerfilDuenoUseCase(provideRepository(context))
    fun provideEliminarCuentaUseCase(context: Context?) = EliminarCuentaUseCase(provideRepository(context))
}

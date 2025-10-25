package com.ucb.perritos.features.perfildueno.data.database

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * Implementación en memoria del DAO para usar cuando no se quiere integrar Room.
 * Solo sirve para el caso simple de obtener/insertar/eliminar un único perfil.
 */
class InMemoryPerfilDuenoDao : IPerfilDuenoDao {

    private val mutex = Mutex()
    private var stored: PerfilDuenoEntity? = null

    override suspend fun obtenerPerfil(): PerfilDuenoEntity? = mutex.withLock { stored }

    override suspend fun insertarPerfil(perfil: PerfilDuenoEntity) = mutex.withLock {
        stored = perfil
    }

    override suspend fun eliminarPerfil() = mutex.withLock {
        stored = null
    }
}


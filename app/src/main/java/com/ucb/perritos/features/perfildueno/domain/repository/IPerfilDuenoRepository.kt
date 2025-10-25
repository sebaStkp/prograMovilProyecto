package com.ucb.perritos.features.perfildueno.domain.repository

import com.ucb.perritos.features.perfildueno.domain.model.PerfilDuenoModel

interface IPerfilDuenoRepository {
    suspend fun obtenerPerfil(): PerfilDuenoModel?
    suspend fun actualizarPerfil(perfil: PerfilDuenoModel)
    suspend fun eliminarCuenta()
}


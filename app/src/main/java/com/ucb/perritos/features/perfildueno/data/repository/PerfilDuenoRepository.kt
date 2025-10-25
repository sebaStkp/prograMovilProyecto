package com.ucb.perritos.features.perfildueno.data.repository

import com.ucb.perritos.features.perfildueno.data.datasource.PerfilDuenoLocalDataSource
import com.ucb.perritos.features.perfildueno.data.mapper.PerfilDuenoMapper
import com.ucb.perritos.features.perfildueno.domain.model.PerfilDuenoModel
import com.ucb.perritos.features.perfildueno.domain.repository.IPerfilDuenoRepository

// Wrapper que delega a la implementación real PerfilDuenoRepositoryImpl
class PerfilDuenoRepository(
    local: PerfilDuenoLocalDataSource,
    mapper: PerfilDuenoMapper
) : IPerfilDuenoRepository by PerfilDuenoRepositoryImpl(local, mapper)

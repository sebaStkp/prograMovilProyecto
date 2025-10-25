package com.ucb.perritos.features.perfildueno.domain.usecase

import com.ucb.perritos.features.perfildueno.domain.model.PerfilDuenoModel
import com.ucb.perritos.features.perfildueno.domain.repository.IPerfilDuenoRepository

class ObtenerPerfilDuenoUseCase(private val repo: IPerfilDuenoRepository) {
    suspend operator fun invoke(): PerfilDuenoModel? = repo.obtenerPerfil()
}


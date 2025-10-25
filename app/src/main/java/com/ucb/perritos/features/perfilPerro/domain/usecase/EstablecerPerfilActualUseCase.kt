package com.ucb.perritos.features.perfilPerro.domain.usecase

import com.ucb.perritos.features.perfilPerro.domain.repository.IPerfilPerroRepository

class EstablecerPerfilActualUseCase(private val repo: IPerfilPerroRepository) {
    suspend operator fun invoke(
        perroId: Long,
        nombre: String,
        raza: String,
        avatarUrl: String?
    ) {
        repo.setPerfilActual(
            perroId = perroId,
            nombre = nombre,
            raza = raza,
            avatarUrl = avatarUrl
        )
    }
}
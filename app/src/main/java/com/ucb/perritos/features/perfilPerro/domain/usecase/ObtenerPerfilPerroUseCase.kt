package com.ucb.perritos.features.perfilPerro.domain.usecase

import com.ucb.perritos.features.perfilPerro.domain.repository.IPerfilPerroRepository

class ObtenerPerfilPerroUseCase(private val repo: IPerfilPerroRepository) {
    operator fun invoke(id: Long) = repo.observePerfil(id)
}
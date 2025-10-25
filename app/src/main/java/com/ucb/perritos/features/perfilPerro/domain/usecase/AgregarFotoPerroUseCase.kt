package com.ucb.perritos.features.perfilPerro.domain.usecase

import com.ucb.perritos.features.perfilPerro.domain.model.FotoPerroModel
import com.ucb.perritos.features.perfilPerro.domain.repository.IFotoPerroRepository

class AgregarFotoPerroUseCase(
    private val repo: IFotoPerroRepository
) {
    suspend operator fun invoke(
        perroId: Long,
        url: String
    ) {
        val nuevaFoto = FotoPerroModel(
            perroId = perroId,
            url = url,
            timestamp = System.currentTimeMillis()
        )
        repo.agregarFoto(nuevaFoto)
    }
}
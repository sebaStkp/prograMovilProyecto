package com.ucb.perritos.features.buscarMascota.domain.usecase

import com.ucb.perritos.features.buscarMascota.domain.model.BuscarMascotaModel
import com.ucb.perritos.features.buscarMascota.domain.repository.IBuscarMascotaRepository

class ObtenerUbicacionActualUseCase(
    private val repository: IBuscarMascotaRepository
) {
    suspend operator fun invoke(): BuscarMascotaModel? {
        return repository.obtenerUbicacionActual()
    }
}
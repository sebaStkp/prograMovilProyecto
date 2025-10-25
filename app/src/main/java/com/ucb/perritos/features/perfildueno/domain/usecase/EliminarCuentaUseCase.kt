package com.ucb.perritos.features.perfildueno.domain.usecase

import com.ucb.perritos.features.perfildueno.domain.repository.IPerfilDuenoRepository

class EliminarCuentaUseCase(private val repo: IPerfilDuenoRepository) {
    suspend operator fun invoke() = repo.eliminarCuenta()
}


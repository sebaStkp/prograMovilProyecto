package com.ucb.perritos.features.login.domain.usecase

import com.ucb.perritos.features.login.domain.model.LoginModel
import com.ucb.perritos.features.login.domain.repository.ILoginRepository

class SetTokenUseCase(
    val repository: ILoginRepository
) {
    suspend operator fun invoke(email: String): Result<Unit> {
        return repository.setToken(email)
    }
}


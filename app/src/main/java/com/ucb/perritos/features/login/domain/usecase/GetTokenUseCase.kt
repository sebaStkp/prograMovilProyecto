package com.ucb.perritos.features.login.domain.usecase


import com.ucb.perritos.features.login.domain.repository.ILoginRepository

class GetTokenUseCase(
    val repository: ILoginRepository
)
{
    suspend fun invoke() : String?{
        return "token"
    }
}
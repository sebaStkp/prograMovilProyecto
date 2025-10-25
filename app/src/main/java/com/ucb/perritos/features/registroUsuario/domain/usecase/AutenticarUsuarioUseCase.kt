package com.ucb.perritos.features.registroUsuario.domain.usecase

import com.ucb.perritos.features.registroUsuario.domain.model.UsuarioModel
import com.ucb.perritos.features.registroUsuario.domain.repository.IRegistroUsuarioRepository

class AutenticarUsuarioUseCase(private val repository: IRegistroUsuarioRepository) {
    suspend operator fun invoke(email: String, password: String): Result<UsuarioModel?> {
        return try {
            val lista = repository.getAllUsuarios()
            var found: UsuarioModel? = null
            for (u in lista) {
                if (u.email == email && u.contraseña == password) {
                    found = u
                    break
                }
            }
            Result.success(found)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

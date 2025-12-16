package com.ucb.perritos.features.registroUsuario.data.repository

import com.ucb.perritos.features.registroUsuario.data.datasource.RegistroUsuarioLocalDataSource
import com.ucb.perritos.features.registroUsuario.domain.model.UsuarioModel
import com.ucb.perritos.features.registroUsuario.domain.repository.IRegistroUsuarioRepository
import io.github.jan.supabase.SupabaseClient // Importante
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from // Importante para .from("perfiles")
class RegistroUsuarioRepository(
    private val registroUsuarioLocalDataSource: RegistroUsuarioLocalDataSource,
    private val supabaseClient: SupabaseClient
): IRegistroUsuarioRepository {
    override suspend fun registrarUsuario(usuario: UsuarioModel): Result<UsuarioModel> {
        return registroUsuarioLocalDataSource.insert(usuario)
    }

    override suspend fun getUsuario(email: String): Result<UsuarioModel> {
        return registroUsuarioLocalDataSource.getUser(email = email)
    }

    override suspend fun getActualUser(): Result<UsuarioModel> {
        return registroUsuarioLocalDataSource.getUsuarioActual()
    }

    // En RegistroUsuarioRepository.kt
    override suspend fun actualizarTokenFCM(token: String) {
        // Usamos supabaseClient (la variable del constructor)
        val userId = supabaseClient.auth.currentUserOrNull()?.id ?: return

        try {
            // Actualizamos la tabla "perfiles"
            supabaseClient.from("perfiles").update(
                {
                    set("fcm_token", token)
                }
            ) {
                filter {
                    eq("id", userId)
                }
            }
            println("✅ Token FCM guardado en la tabla perfiles para el usuario $userId")
        } catch (e: Exception) {
            println("❌ Error guardando token: ${e.message}")
        }
    }

}
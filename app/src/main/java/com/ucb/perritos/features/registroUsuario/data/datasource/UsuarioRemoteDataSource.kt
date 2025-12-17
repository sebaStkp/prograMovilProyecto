package com.ucb.perritos.features.registroUsuario.data.datasource

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from

class RegistroUsuarioRemoteDataSource(
    private val supabaseClient: SupabaseClient
) {
    suspend fun actualizarTokenFCM(token: String) {
        val userId = supabaseClient.auth.currentUserOrNull()?.id ?: return

        try {
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
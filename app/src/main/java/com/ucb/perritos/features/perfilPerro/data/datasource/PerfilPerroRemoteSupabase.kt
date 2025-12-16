package com.ucb.perritos.features.perfilPerro.data.datasource

import com.ucb.perritos.features.registroMascota.data.dto.PerroDto
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from

class PerfilPerroRemoteSupabase(private val supabase: SupabaseClient) {

    suspend fun getPerroById(perroId: Long): PerroDto {
        return supabase.from("perritos")
            .select {
                filter { eq("id", perroId) }
                limit(1)
            }
            .decodeSingle<PerroDto>()
    }
}

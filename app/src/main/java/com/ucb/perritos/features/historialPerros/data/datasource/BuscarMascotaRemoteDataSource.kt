package com.ucb.perritos.features.historialPerros.data.datasource

import com.ucb.perritos.features.historialPerros.domain.model.HistorialUbicacionModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from

class BuscarMascotaRemoteDataSource(
    private val supabaseClient: SupabaseClient
) {
    suspend fun guardarHistorialUbicacion(historial: HistorialUbicacionModel): Result<Boolean> {
        return try {
            supabaseClient.from("historial_ubicaciones").insert(historial)
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
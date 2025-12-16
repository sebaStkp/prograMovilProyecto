package com.ucb.perritos.features.core

import android.content.Context
import androidx.core.content.edit
import io.github.jan.supabase.auth.SessionManager
import io.github.jan.supabase.auth.user.UserSession // <--- Importante
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SessionManagerAndroid(val context: Context) : SessionManager {

    private val prefs = context.getSharedPreferences("supabase_session", Context.MODE_PRIVATE)

    // Configuramos el convertidor JSON (para pasar de Objeto a Texto)
    private val json = Json { ignoreUnknownKeys = true }

    // GUARDAR: Recibimos el objeto UserSession, lo volvemos texto y guardamos
    override suspend fun saveSession(session: UserSession) {
        val sessionString = json.encodeToString(session)
        prefs.edit { putString("session", sessionString) }
    }

    // LEER: Leemos el texto, lo volvemos objeto UserSession y devolvemos
    override suspend fun loadSession(): UserSession? {
        val sessionString = prefs.getString("session", null) ?: return null
        return try {
            json.decodeFromString<UserSession>(sessionString)
        } catch (e: Exception) {
            // Si el texto está corrupto, devolvemos null
            null
        }
    }

    // BORRAR
    override suspend fun deleteSession() {
        prefs.edit { remove("session") }
    }
}
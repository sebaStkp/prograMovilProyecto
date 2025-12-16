package com.ucb.perritos.features.login.data.repository



import com.google.firebase.messaging.FirebaseMessaging
import com.ucb.perritos.features.login.domain.repository.ILoginRepository

import com.ucb.perritos.features.login.data.datasource.LoginDataStore
import com.ucb.perritos.features.login.domain.model.LoginModel
import com.ucb.perritos.features.registroUsuario.domain.model.UsuarioModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.tasks.await


class LoginRepository(
    private val loginDataStore: LoginDataStore,
    private val supabaseClient: SupabaseClient
) : ILoginRepository {

    override suspend fun getToken(nickname: String): Result<LoginModel> {
        return try {

            val fakeToken = "token_${nickname}_123"
            val user = LoginModel(nickname = nickname, token = fakeToken)

            loginDataStore.saveUserName(nickname)
//            loginDataStore.saveToken(fakeToken)

            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun setToken(email: String): Result<Unit> {
        return try {
            // 1. OBTENER EL TOKEN REAL DE FIREBASE (Del dispositivo actual)
            // Esto le pregunta al celular: "¿Quién eres para las notificaciones?"
            val tokenReal = FirebaseMessaging.getInstance().token.await()

            println("DEBUG: Token FCM obtenido del celular: $tokenReal")

            // 2. GUARDARLO EN SUPABASE USANDO EL EMAIL COMO "LLAVE"
            // Traducido: "Actualiza la tabla perfiles, pon el fcm_token = tokenReal
            // DONDE el email sea igual al email que recibí".
            supabaseClient.from("perfiles").update(
                {
                    set("fcm_token", tokenReal)
                }
            ) {
                filter {
                    eq("email", email)
                }
            }

            // 3. (Opcional) Guardarlo local también
            loginDataStore.saveToken(tokenReal)

            Result.success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }



}

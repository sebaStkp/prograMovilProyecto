package com.ucb.perritos.features.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.perritos.features.login.domain.usecase.SetTokenUseCase
import com.ucb.perritos.features.registroUsuario.domain.model.UsuarioModel
import com.ucb.perritos.features.registroUsuario.domain.usecase.GetUserUseCase
import com.ucb.perritos.features.registroUsuario.domain.usecase.RegistrarUsuarioUseCase
import io.github.jan.supabase.SupabaseClient // <--- IMPORTANTE
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val registrarUsuarioUseCase: RegistrarUsuarioUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val usecaseSetToken: SetTokenUseCase,
    private val supabaseClient: SupabaseClient // <--- AGREGADO AQUÍ
) : ViewModel() {

    sealed class LoginStateUI {
        object Init : LoginStateUI()
        object Loading : LoginStateUI()
        class Error(val message: String) : LoginStateUI()
        class Success(val mensaje: String, val irAlMapa: Boolean) : LoginStateUI()
    }

    private val _state = MutableStateFlow<LoginStateUI>(LoginStateUI.Init)
    val state: StateFlow<LoginStateUI> = _state.asStateFlow()

    fun iniciarSesion(email: String, pass: String) {
        if (email.isBlank() || pass.isBlank()) {
            _state.value = LoginStateUI.Error("Por favor ingrese correo y contraseña")
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            _state.value = LoginStateUI.Loading

            try {
                // USAMOS supabaseClient (la variable del constructor)
                supabaseClient.auth.signInWith(Email) {
                    this.email = email
                    this.password = pass
                }

                val userSupabase = supabaseClient.auth.currentUserOrNull()
                val nombreGuardado = userSupabase?.userMetadata?.get("nombre_dueno")
                    ?.toString()?.replace("\"", "") ?: "Usuario"

                val existeEnRoom = getUserUseCase.invoke(email).isSuccess
                val irAlMapa = existeEnRoom

                registrarUsuarioUseCase.invoke(
                    UsuarioModel(
                        email = email,
                        nombreDueño = if(nombreGuardado.isNotEmpty()) nombreGuardado else "Usuario",
                        contraseña = pass
                    )
                )
                println("DEBUG: Guardando token para $email...")
                val resultadoToken = usecaseSetToken.invoke(email)

                if (resultadoToken.isSuccess) {
                    println("DEBUG: ¡Token guardado exitosamente en Supabase!")
                } else {
                    println("DEBUG: Error al guardar token: ${resultadoToken.exceptionOrNull()?.message}")
                }

                usecaseSetToken.invoke(email)

                _state.value = LoginStateUI.Success("Bienvenido $nombreGuardado", irAlMapa)

            } catch (e: Exception) {
                val errorMsg = when {
                    e.message?.contains("Email not confirmed") == true -> "Tu correo no ha sido confirmado."
                    e.message?.contains("Invalid login credentials") == true -> "Credenciales incorrectas."
                    else -> "Error: ${e.message}"
                }
                _state.value = LoginStateUI.Error(errorMsg)
            }
        }
    }
}
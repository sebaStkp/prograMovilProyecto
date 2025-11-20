package com.ucb.perritos.features.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.perritos.features.core.supabase
import com.ucb.perritos.features.login.domain.usecase.SetTokenUseCase
import com.ucb.perritos.features.registroUsuario.domain.model.UsuarioModel
import com.ucb.perritos.features.registroUsuario.domain.usecase.RegistrarUsuarioUseCase
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val registrarUsuarioUseCase: RegistrarUsuarioUseCase,
    private val usecaseSetToken: SetTokenUseCase
) : ViewModel() {

    sealed class LoginStateUI {
        object Init : LoginStateUI()
        object Loading : LoginStateUI()
        class Error(val message: String) : LoginStateUI()
        class Success(val mensaje: String) : LoginStateUI()
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
                supabase.auth.signInWith(Email) {
                    this.email = email
                    this.password = pass
                }

                val userSupabase = supabase.auth.currentUserOrNull()
                val nombreGuardado = userSupabase?.userMetadata?.get("nombre_dueno")
                    ?.toString()?.replace("\"", "") ?: "Usuario"

                registrarUsuarioUseCase.invoke(
                    UsuarioModel(
                        email = email,
                        nombreDueño = nombreGuardado,
                        contraseña = pass
                    )
                )

                val resultToken = usecaseSetToken.invoke(email)

                resultToken.fold(
                    onSuccess = {
                        _state.value = LoginStateUI.Success("Bienvenido $nombreGuardado")
                    },
                    onFailure = {
                        _state.value = LoginStateUI.Error("Login correcto, pero falló la generación del Token")
                    }
                )

            } catch (e: Exception) {
                val errorMsg = when {
                    e.message?.contains("Email not confirmed") == true -> "Tu correo no ha sido confirmado. Revisa tu bandeja de entrada."
                    e.message?.contains("Invalid login credentials") == true -> "Correo o contraseña incorrectos."
                    else -> "Error: ${e.message}"
                }
                _state.value = LoginStateUI.Error(errorMsg)
            }
        }
    }
}
package com.ucb.perritos.features.registroUsuario.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.perritos.features.core.supabase

import com.ucb.perritos.features.registroUsuario.domain.model.UsuarioModel
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

import io.github.jan.supabase.auth.auth


class RegistroUsuarioViewModel : ViewModel() {


    sealed class RegistrarUsuarioStateUI {
        object Init: RegistrarUsuarioStateUI()
        object Loading: RegistrarUsuarioStateUI()
        class Error(val message: String): RegistrarUsuarioStateUI()

        class Success(val message: String): RegistrarUsuarioStateUI()
    }

    private val _state = MutableStateFlow<RegistrarUsuarioStateUI>(RegistrarUsuarioStateUI.Init)
    val state : StateFlow<RegistrarUsuarioStateUI> = _state.asStateFlow()

    fun registrarUsuario(usuario: UsuarioModel) {

        val email = usuario.email
        val pass = usuario.contraseña
        val nombre = usuario.nombreDueño

        if (email.isNullOrBlank() || pass.isNullOrBlank() || nombre.isNullOrBlank()) {
            _state.value = RegistrarUsuarioStateUI.Error("Por favor, llena todos los campos.")
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            _state.value = RegistrarUsuarioStateUI.Loading

            try {

                supabase.auth.signUpWith(Email) {
                    this.email = email
                    this.password = pass
                    data = buildJsonObject {
                        put("nombre_dueno", nombre)
                    }
                }

                _state.value = RegistrarUsuarioStateUI.Success("¡Cuenta creada! Revisa tu correo ${email} para activarla.")

            } catch (e: Exception) {

                val errorMsg = when {
                    e.message?.contains("User already registered") == true -> "Este correo ya está registrado."
                    e.message?.contains("Password should be") == true -> "La contraseña es muy débil (mínimo 6 caracteres)."
                    e.message?.contains("Validation failed") == true -> "El email no es válido."
                    else -> "Ocurrió un error: ${e.message}"
                }
                _state.value = RegistrarUsuarioStateUI.Error(errorMsg)
            }
        }
    }
}
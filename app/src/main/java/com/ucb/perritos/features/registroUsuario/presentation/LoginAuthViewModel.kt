package com.ucb.perritos.features.registroUsuario.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.perritos.features.registroUsuario.domain.usecase.AutenticarUsuarioUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginAuthViewModel(private val autenticarUsuarioUseCase: AutenticarUsuarioUseCase) : ViewModel() {

    sealed class AuthState {
        object Idle: AuthState()
        object Loading: AuthState()
        class Success(val email: String): AuthState()
        class Error(val message: String): AuthState()
    }

    private val _state = MutableStateFlow<AuthState>(AuthState.Idle)
    val state: StateFlow<AuthState> = _state.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = AuthState.Loading
            try {
                val result = autenticarUsuarioUseCase.invoke(email, password)
                result.fold(onSuccess = { user ->
                    if (user != null) {
                        _state.value = AuthState.Success(user.email ?: "")
                    } else {
                        _state.value = AuthState.Error("Credenciales incorrectas")
                    }
                }, onFailure = {
                    _state.value = AuthState.Error("Error interno")
                })
            } catch (e: Exception) {
                _state.value = AuthState.Error("Error interno")
            }
        }
    }
}


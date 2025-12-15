package com.ucb.perritos.features.perfilUsuario.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.perritos.features.registroUsuario.domain.model.UsuarioModel
import com.ucb.perritos.features.registroUsuario.domain.usecase.GetUsuarioActual
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PerfilUsuarioViewModel(
    private val getUsuarioActualUseCase: GetUsuarioActual
) : ViewModel() {

    private val _state = MutableStateFlow<PerfilUiState>(PerfilUiState.Loading)
    val state: StateFlow<PerfilUiState> = _state.asStateFlow()

    init {
        cargarPerfil()
    }

    private fun cargarPerfil() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = PerfilUiState.Loading
            try {
                // Invocamos el caso de uso que trae el último usuario de la BD local
                val result = getUsuarioActualUseCase.invoke()

                result.onSuccess { usuario ->
                    _state.value = PerfilUiState.Success(usuario)
                }.onFailure { exception ->
                    _state.value = PerfilUiState.Error("Error: ${exception.message}")
                }

            } catch (e: Exception) {
                _state.value = PerfilUiState.Error("Error desconocido: ${e.message}")
            }
        }
    }

    sealed class PerfilUiState {
        object Loading : PerfilUiState()
        data class Success(val usuario: UsuarioModel) : PerfilUiState()
        data class Error(val mensaje: String) : PerfilUiState()
    }
}
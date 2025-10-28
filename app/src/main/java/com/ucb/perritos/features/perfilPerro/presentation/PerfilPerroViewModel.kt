package com.ucb.perritos.features.perfilPerro.presentation

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.ucb.perritos.features.perfilPerro.domain.model.PerfilPerroModel
import com.ucb.perritos.features.perfilPerro.domain.usecase.ObtenerPerfilPerroUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PerfilPerroUiState(
    val perfil: PerfilPerroModel? = null,
    val loading: Boolean = true
)

class PerfilPerroViewModel(
    private val getPerfil: ObtenerPerfilPerroUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PerfilPerroUiState())
    val state: StateFlow<PerfilPerroUiState> = _state.asStateFlow()

    fun init(perroId: Long) {
        viewModelScope.launch {
            getPerfil(perroId).collect { perfil ->
                _state.update { it.copy(perfil = perfil, loading = false) }
            }
        }
    }

    fun agregarFoto(perroId: Long, imageUri: Uri) {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            // Podrías usar el use case aquí
            // agregarFotoPerroUseCase(perroId, imageUri)
            _state.update { it.copy(loading = false) }
        }
    }

}
package com.ucb.perritos.features.perfildueno.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.perritos.features.perfildueno.domain.model.PerfilDuenoModel
import com.ucb.perritos.features.perfildueno.domain.usecase.ObtenerPerfilDuenoUseCase
import com.ucb.perritos.features.perfildueno.domain.usecase.EliminarCuentaUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PerfilDuenoViewModel(private val obtenerPerfil: ObtenerPerfilDuenoUseCase,
                           private val eliminarCuentaUseCase: EliminarCuentaUseCase
) : ViewModel() {

    private val _perfil = MutableStateFlow<PerfilDuenoModel?>(null)
    val perfil: StateFlow<PerfilDuenoModel?> = _perfil

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun cargarPerfil() {
        viewModelScope.launch {
            _loading.value = true
            try {
                val p = obtenerPerfil()
                _perfil.value = p
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

    fun eliminarCuenta(onDeleted: () -> Unit = {}) {
        viewModelScope.launch {
            _loading.value = true
            try {
                eliminarCuentaUseCase()
                // Clear perfil in state
                _perfil.value = null
                onDeleted()
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }
}

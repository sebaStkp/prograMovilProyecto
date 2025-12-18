package com.ucb.perritos.features.historialPerros.presentation

import com.ucb.perritos.features.historialPerros.domain.model.HistorialUbicacionModel
import com.ucb.perritos.features.historialPerros.domain.repository.IHistorialPerrosRepository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HistorialPerroViewModel(
    private val repository: IHistorialPerrosRepository
) : ViewModel() {


    sealed class HistorialStateUI {
        object Init : HistorialStateUI()
        object Loading : HistorialStateUI()
        class Error(val message: String) : HistorialStateUI()
        class Success(val lista: List<HistorialUbicacionModel>) : HistorialStateUI()
    }

    private val _state = MutableStateFlow<HistorialStateUI>(HistorialStateUI.Init)
    val state: StateFlow<HistorialStateUI> = _state.asStateFlow()

    /**
     * Carga el historial desde Supabase
     */
    fun cargarHistorial(perroId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = HistorialStateUI.Loading

            repository.obtenerHistorialPorPerro(perroId).onSuccess { lista ->
                _state.value = HistorialStateUI.Success(lista)
            }.onFailure { e ->
                _state.value = HistorialStateUI.Error("Error al cargar historial: ${e.message}")
            }
        }
    }


    fun ejecutarSimulacionYGuardar(perroId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val latSimulada = -17.38195
                val lonSimulada = -66.15995
                val nombreSimulado = "Plaza Colón"

                val nuevoPunto = HistorialUbicacionModel(
                    perro_id = perroId,
                    latitud = latSimulada,
                    longitud = lonSimulada,

                )

                val resultado = repository.guardarUbicacionEnHistorial(nuevoPunto)

                if (resultado.isSuccess) {

                    cargarHistorial(perroId)
                }

            } catch (e: Exception) {
                _state.value = HistorialStateUI.Error("Fallo en simulación: ${e.message}")
            }
        }
    }
}
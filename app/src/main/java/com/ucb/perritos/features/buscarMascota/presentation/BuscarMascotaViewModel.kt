package com.ucb.perritos.features.buscarMascota.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.perritos.features.buscarMascota.domain.model.BuscarMascotaModel
import com.ucb.perritos.features.buscarMascota.domain.usecase.ObtenerUbicacionActualUseCase
import com.ucb.perritos.features.registroMascota.domain.model.RegistroPerroModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BuscarMascotaViewModel(
    private val obtenerUbicacionActualUseCase: ObtenerUbicacionActualUseCase
): ViewModel() {
    sealed class BuscarMascotaViewModelStateUI {
        object Init : BuscarMascotaViewModelStateUI()
        object Loading : BuscarMascotaViewModelStateUI()
        class Error(val message: String) : BuscarMascotaViewModelStateUI()
        class Success(val ubicacion: BuscarMascotaModel) : BuscarMascotaViewModelStateUI() // Usa BuscarMascotaModel en lugar de RegistroPerroModel
    }

    private val _state = MutableStateFlow<BuscarMascotaViewModelStateUI>(BuscarMascotaViewModelStateUI.Init)

    val state : StateFlow<BuscarMascotaViewModelStateUI> = _state.asStateFlow()

    fun cargarUbicacion() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = BuscarMascotaViewModelStateUI.Loading
            try {
                val ubicacion = obtenerUbicacionActualUseCase()
                if (ubicacion != null) {
                    _state.value = BuscarMascotaViewModelStateUI.Success(ubicacion)
                } else {
                    _state.value = BuscarMascotaViewModelStateUI.Error("No se pudo obtener la ubicación.")
                }
            } catch (e: Exception) {
                _state.value = BuscarMascotaViewModelStateUI.Error("Error al obtener la ubicación: ${e.message}")
            }
        }
    }
}
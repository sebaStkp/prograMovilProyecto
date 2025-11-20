package com.ucb.perritos.features.buscarMascota.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.perritos.features.buscarMascota.domain.model.BuscarMascotaModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint


class BuscarMascotaViewModel : ViewModel() {

    sealed class BuscarMascotaViewModelStateUI {
        object Init : BuscarMascotaViewModelStateUI()
        object Loading : BuscarMascotaViewModelStateUI()
        class Error(val message: String) : BuscarMascotaViewModelStateUI()
        class Success(val ubicacion: BuscarMascotaModel) : BuscarMascotaViewModelStateUI()
    }

    private val _state = MutableStateFlow<BuscarMascotaViewModelStateUI>(BuscarMascotaViewModelStateUI.Init)
    val state: StateFlow<BuscarMascotaViewModelStateUI> = _state.asStateFlow()


    private val _mapCenter = MutableStateFlow(GeoPoint(-17.3935, -66.1570))
    val mapCenter: StateFlow<GeoPoint> = _mapCenter.asStateFlow()

    val ubicacionActual = MutableStateFlow("Cargando...")
    val ultimaUbicacion = MutableStateFlow("Desconocida")

    fun cargarUbicacion() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = BuscarMascotaViewModelStateUI.Loading



            try {

                val lat = -17.3935
                val lon = -66.1570
                val direccionFalsa = "Av. América, Cochabamba"

                val ubicacionMock = BuscarMascotaModel(
                    latitud = lat,
                    longitud = lon,
                    direccion = direccionFalsa,

                )


                _mapCenter.value = GeoPoint(lat, lon)
                ubicacionActual.value = direccionFalsa
                ultimaUbicacion.value = "Ubicación de prueba"

                _state.value = BuscarMascotaViewModelStateUI.Success(ubicacionMock)

            } catch (e: Exception) {
                _state.value = BuscarMascotaViewModelStateUI.Error("Error simulado: ${e.message}")
            }
        }
    }
}
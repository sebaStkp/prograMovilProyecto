package com.ucb.perritos.features.buscarMascota.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.perritos.features.buscarMascota.data.remote.UbicacionApi
import com.ucb.perritos.features.buscarMascota.domain.model.BuscarMascotaModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint

class BuscarMascotaViewModel(
    private val api: UbicacionApi
) : ViewModel() {

    sealed class BuscarMascotaViewModelStateUI {
        object Init : BuscarMascotaViewModelStateUI()
        object Loading : BuscarMascotaViewModelStateUI()
        class Error(val message: String) : BuscarMascotaViewModelStateUI()

        // Dos puntos: yo (origin) y mascota (current)
        class Success(val origin: BuscarMascotaModel, val pet: BuscarMascotaModel) : BuscarMascotaViewModelStateUI()
    }

    private val _state = MutableStateFlow<BuscarMascotaViewModelStateUI>(BuscarMascotaViewModelStateUI.Init)
    val state: StateFlow<BuscarMascotaViewModelStateUI> = _state.asStateFlow()

    private var pollingJob: Job? = null

    // Muestra “mi ubicación” fija sin depender del servidor (para demo)
    fun cargarUbicacionInicial() {
        val origin = BuscarMascotaModel(
            latitud = -17.3935,
            longitud = -66.1570,
            direccion = "Mi ubicación (demo)"
        )

        // Mascota inicial (igual que origin al inicio, solo para que no esté vacío)
        val pet = BuscarMascotaModel(
            latitud = -17.3935,
            longitud = -66.1570,
            direccion = "Mascota (demo)"
        )

        _state.value = BuscarMascotaViewModelStateUI.Success(origin, pet)
    }

    fun iniciarBusquedaMascota() {
        pollingJob?.cancel()

        pollingJob = viewModelScope.launch(Dispatchers.IO) {

            val currentState = _state.value
            val origin = if (currentState is BuscarMascotaViewModelStateUI.Success) currentState.origin
            else BuscarMascotaModel(-17.3935, -66.1570, "Mi ubicación (demo)")

            // SOLO si estás Init, muestra loading
            if (currentState is BuscarMascotaViewModelStateUI.Init) {
                _state.value = BuscarMascotaViewModelStateUI.Loading
            }

            while (true) {
                try {
                    val resp = api.getUbicacion()

                    val pet = BuscarMascotaModel(
                        latitud = resp.current.latitud,
                        longitud = resp.current.longitud,
                        direccion = "Mascota (simulada)"
                    )

                    _state.value = BuscarMascotaViewModelStateUI.Success(origin, pet)
                    delay(1500)
                } catch (e: Exception) {
                    _state.value = BuscarMascotaViewModelStateUI.Error("Error servidor: ${e.message}")
                    break
                }
            }
        }
    }

//    fun iniciarBusquedaMascota() {
//        pollingJob?.cancel()
//
//        pollingJob = viewModelScope.launch(Dispatchers.IO) {
//            _state.value = BuscarMascotaViewModelStateUI.Loading
//
//            // Si ya estabas mostrando Success, lo respetamos
//            val currentState = _state.value
//            val origin = if (currentState is BuscarMascotaViewModelStateUI.Success) currentState.origin
//            else BuscarMascotaModel(-17.3935, -66.1570, "Mi ubicación (demo)")
//
//            while (true) {
//                try {
//                    val resp = api.getUbicacion()
//
//                    val pet = BuscarMascotaModel(
//                        latitud = resp.current.latitud,
//                        longitud = resp.current.longitud,
//                        direccion = "Mascota (simulada)"
//                    )
//
//                    _state.value = BuscarMascotaViewModelStateUI.Success(origin, pet)
//                    delay(1500) // cada 1.5s se mueve la patita
//                } catch (e: Exception) {
//                    _state.value = BuscarMascotaViewModelStateUI.Error("Error servidor: ${e.message}")
//                    break
//                }
//            }
//        }
//    }

    override fun onCleared() {
        pollingJob?.cancel()
        super.onCleared()
    }
}



//class BuscarMascotaViewModel : ViewModel() {
//
//    sealed class BuscarMascotaViewModelStateUI {
//        object Init : BuscarMascotaViewModelStateUI()
//        object Loading : BuscarMascotaViewModelStateUI()
//        class Error(val message: String) : BuscarMascotaViewModelStateUI()
//        class Success(val ubicacion: BuscarMascotaModel) : BuscarMascotaViewModelStateUI()
//    }
//
//    private val _state = MutableStateFlow<BuscarMascotaViewModelStateUI>(BuscarMascotaViewModelStateUI.Init)
//    val state: StateFlow<BuscarMascotaViewModelStateUI> = _state.asStateFlow()
//
//
//    private val _mapCenter = MutableStateFlow(GeoPoint(-17.3935, -66.1570))
//    val mapCenter: StateFlow<GeoPoint> = _mapCenter.asStateFlow()
//
//    val ubicacionActual = MutableStateFlow("Cargando...")
//    val ultimaUbicacion = MutableStateFlow("Desconocida")
//
//    fun cargarUbicacion() {
//        viewModelScope.launch(Dispatchers.IO) {
//            _state.value = BuscarMascotaViewModelStateUI.Loading
//
//
//
//            try {
//
//                val lat = -17.3935
//                val lon = -66.1570
//                val direccionFalsa = "Av. América, Cochabamba"
//
//                val ubicacionMock = BuscarMascotaModel(
//                    latitud = lat,
//                    longitud = lon,
//                    direccion = direccionFalsa,
//
//                )
//
//
//                _mapCenter.value = GeoPoint(lat, lon)
//                ubicacionActual.value = direccionFalsa
//                ultimaUbicacion.value = "Ubicación de prueba"
//
//                _state.value = BuscarMascotaViewModelStateUI.Success(ubicacionMock)
//
//            } catch (e: Exception) {
//                _state.value = BuscarMascotaViewModelStateUI.Error("Error simulado: ${e.message}")
//            }
//        }
//    }
//}
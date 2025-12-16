package com.ucb.perritos.features.perrosRegistrados.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
//import com.ucb.perritos.features.core.supabase
import com.ucb.perritos.features.registroMascota.domain.usecase.ObtenerPerrosUseCase
import com.ucb.perritos.features.registroMascota.data.dto.PerroDto
import com.ucb.perritos.features.registroMascota.domain.model.PerroModel
import com.ucb.perritos.features.registroMascota.domain.usecase.RegistrarPerroUseCase
import com.ucb.perritos.features.registroUsuario.domain.model.UsuarioModel
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class PerrosRegistradosViewModel(
    private val obtenerPerrosUseCase: ObtenerPerrosUseCase,
    private val supabaseClient: SupabaseClient
): ViewModel() {


    sealed class PerrosRegistradosStateUI {
        object Init: PerrosRegistradosStateUI()
        object Loading: PerrosRegistradosStateUI()
        class Error(val message: String): PerrosRegistradosStateUI()

        class Success(val perros: List<PerroDto>) : PerrosRegistradosStateUI()
        object Empty : PerrosRegistradosStateUI()
    }

    private val _state = MutableStateFlow<PerrosRegistradosStateUI>(PerrosRegistradosStateUI.Init)
    val state : StateFlow<PerrosRegistradosStateUI> = _state.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<Int>()
    val navigationEvent: SharedFlow<Int> = _navigationEvent.asSharedFlow()

    fun onEditarPerroClicked(idPerro: Int) {
        viewModelScope.launch {
            _navigationEvent.emit(idPerro)
        }
    }

    fun cargarMisPerros() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = PerrosRegistradosStateUI.Loading

            try {
                val currentUser = supabaseClient.auth.currentUserOrNull()
                val userId = currentUser?.id

                if (userId != null) {
                    val result = obtenerPerrosUseCase.invoke(userId)

                    result.fold(
                        onSuccess = { listaPerros ->
                            if (listaPerros.isEmpty()) {
                                _state.value = PerrosRegistradosStateUI.Empty
                            } else {
                                _state.value = PerrosRegistradosStateUI.Success(listaPerros)
                            }
                        },
                        onFailure = { error ->
                            _state.value = PerrosRegistradosStateUI.Error("Error obteniendo perros: ${error.message}")
                        }
                    )
                } else {
                    _state.value = PerrosRegistradosStateUI.Error("No hay sesión activa.")
                }

            } catch (e: Exception) {
                _state.value = PerrosRegistradosStateUI.Error("Excepción: ${e.message}")
            }
        }
    }

}
package com.ucb.perritos.features.registroMascota.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.perritos.features.core.supabase
import com.ucb.perritos.features.perfilPerro.domain.usecase.EstablecerPerfilActualUseCase
import com.ucb.perritos.features.perrosRegistrados.domain.EditarPerroUseCase
import com.ucb.perritos.features.registroMascota.data.dto.PerroDto
import com.ucb.perritos.features.registroMascota.domain.model.PerroModel
import com.ucb.perritos.features.registroMascota.domain.usecase.RegistrarPerroUseCase
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class RegistroPerroViewModel(
    private val registrarPerroUseCase: RegistrarPerroUseCase,
    private val establecerPerfilActualUseCase: EstablecerPerfilActualUseCase,
    private val editarPerroUseCase: EditarPerroUseCase,
    private val appContext: android.content.Context,
): ViewModel() {

    sealed class RegistrarPerroStateUI {
        object Init: RegistrarPerroStateUI()
        object Loading: RegistrarPerroStateUI()
        class Error(val message: String): RegistrarPerroStateUI()
        class Success(val perrito: PerroModel): RegistrarPerroStateUI()
    }
    private val _state = MutableStateFlow<RegistrarPerroStateUI>(RegistrarPerroStateUI.Init)
    val state : StateFlow<RegistrarPerroStateUI> = _state.asStateFlow()

    private val _perroIdToEdit = MutableStateFlow<Int?>(null)
    private val _isEditing = MutableStateFlow(false)
    val isEditing: StateFlow<Boolean> = _isEditing.asStateFlow()


    fun iniciarModoEdicion(id: Int, perroDatos: PerroModel?) {
        _isEditing.value = true
        _perroIdToEdit.value = id
    }


    fun actualizarPerro(perro: PerroModel) {
        val id = _perroIdToEdit.value

        if (id == null) {
            _state.value = RegistrarPerroStateUI.Error("No se encontró el ID del perro a editar")
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = RegistrarPerroStateUI.Loading

            val result = editarPerroUseCase.invoke(perro, id)

            result.fold(
                onSuccess = { perroEditado ->
                    _state.value = RegistrarPerroStateUI.Success(perroEditado)
                },
                onFailure = { error ->
                    _state.value = RegistrarPerroStateUI.Error(error.message ?: "Error al editar")
                }
            )
        }
    }
    fun registrarPerro(perro: PerroModel, avatarUri: android.net.Uri?) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = RegistrarPerroStateUI.Loading

            val avatarBytes: ByteArray? = avatarUri?.let { uri ->
                appContext.contentResolver.openInputStream(uri)?.use { input ->
                    input.readBytes()
                }
            }

            val result = registrarPerroUseCase.invoke(perro, avatarBytes)

            result.fold(
                onSuccess = { perroGuardado ->

                    try {
                        establecerPerfilActualUseCase(
                            perroId = 1L,
                            nombre = perroGuardado.nombre_perro.orEmpty(),
                            raza = perroGuardado.raza.orEmpty(),
                            avatarUrl = "https://picsum.photos/200"
                        )
                        _state.value = RegistrarPerroStateUI.Success(perroGuardado)
                    } catch (e: Exception) {

                        _state.value = RegistrarPerroStateUI.Success(perroGuardado)
                    }
                },
                onFailure = { error ->
                    val message = error.message ?: "Error al registrar perro"
                    _state.value = RegistrarPerroStateUI.Error(message = message)
                }
            )
        }
    }
}
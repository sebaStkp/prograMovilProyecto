package com.ucb.perritos.features.registroUsuario.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.perritos.features.registroUsuario.domain.model.UsuarioModel
import com.ucb.perritos.features.registroUsuario.domain.usecase.RegistrarUsuarioUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegistroUsuarioViewModel(
    val usecase: RegistrarUsuarioUseCase,
) : ViewModel() {
    sealed class RegistrarUsuarioStateUI {
        object Init: RegistrarUsuarioStateUI()
        object Loading: RegistrarUsuarioStateUI()
        class Error(val message: String): RegistrarUsuarioStateUI()
        class Success(val usuario: UsuarioModel): RegistrarUsuarioStateUI()
    }
    private val _state = MutableStateFlow<RegistrarUsuarioStateUI>(RegistrarUsuarioStateUI.Init)

    val state : StateFlow<RegistrarUsuarioStateUI> = _state.asStateFlow()

    fun registrarUsuario(usuario: UsuarioModel) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = RegistrarUsuarioStateUI.Loading
            val result = usecase.invoke(usuario)

            result.fold(
                onSuccess = {
                        usuario -> _state.value = RegistrarUsuarioStateUI.Success(usuario)
                },
                onFailure = {
                    val message = "Error al registrar usuario"

                    _state.value = RegistrarUsuarioStateUI.Error(message = message)
                }
            )
        }
    }
}
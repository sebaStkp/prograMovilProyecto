package com.ucb.perritos.features.registroMascota.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.perritos.features.registroMascota.domain.model.RegistroPerroModel
import com.ucb.perritos.features.registroMascota.domain.usecase.RegistrarPerroUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.invoke

class RegistroPerroViewModel(
    val usecase: RegistrarPerroUseCase,
): ViewModel() {
        sealed class RegistrarPerroStateUI {
            object Init: RegistrarPerroStateUI()
            object Loading: RegistrarPerroStateUI()
            class Error(val message: String): RegistrarPerroStateUI()
            class Success(val perrito: RegistroPerroModel): RegistrarPerroStateUI()
        }
        private val _state = MutableStateFlow<RegistrarPerroStateUI>(RegistrarPerroStateUI.Init)

        val state : StateFlow<RegistrarPerroStateUI> = _state.asStateFlow()

    fun registrarPerro(perro: RegistroPerroModel) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = RegistrarPerroStateUI.Loading
            val result = usecase.invoke(perro)

            result.fold(
                onSuccess = {
                    perro -> _state.value = RegistrarPerroStateUI.Success( perro )
                },
                onFailure = {
                    val message = "Error al registrar perro"

                    _state.value = RegistrarPerroStateUI.Error(message = message)
                }
            )
        }
    }

}
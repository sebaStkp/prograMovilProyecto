package com.ucb.perritos.features.bienvenida.presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.perritos.features.bienvenida.domain.usecase.IrInicioSesionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BienvenidaViewModel(
    val irInicioSesionUseCase: IrInicioSesionUseCase
) : ViewModel(

) {
    sealed class BienvenidaUIState {
        object Init : BienvenidaUIState()
        object Loading: BienvenidaUIState()
        class Error(val message: String): BienvenidaUIState()
//        object NavigateToLogin : BienvenidaUIState()
    }

    private val _state = MutableStateFlow<BienvenidaUIState>(BienvenidaUIState.Init)
    val state: StateFlow<BienvenidaUIState> = _state.asStateFlow()


    fun onLoginClick() {
        viewModelScope.launch {
            _state.value = BienvenidaUIState.Loading
            try {
                irInicioSesionUseCase.invoke()
                _state.value = BienvenidaUIState.Init
            } catch (e: Exception) {
                _state.value = BienvenidaUIState.Error(e.message ?: "Error desconocido")
            }
        }
    }


}

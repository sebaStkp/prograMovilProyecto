package com.ucb.perritos.features.bienvenida.presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BienvenidaViewModel: ViewModel(

) {
    sealed class BienvenidaUIState {
        object Init : BienvenidaUIState()
        object Success : BienvenidaUIState()
    }

    private val _state = MutableStateFlow<BienvenidaUIState>(BienvenidaUIState.Init)
    val state: StateFlow<BienvenidaUIState> = _state.asStateFlow()


}

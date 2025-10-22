package com.ucb.perritos.features.menu.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.perritos.features.menu.domain.model.MenuModel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class MenuViewModel(

):ViewModel() {
    sealed class MenuStateUI {
        object Init: MenuStateUI()
        object Loading: MenuStateUI()
        class Error(val message: String): MenuStateUI()
        class Success(val menu: MenuModel): MenuStateUI()
    }
    private val _state = MutableStateFlow<MenuStateUI>(MenuStateUI.Init)

    val state : StateFlow<MenuStateUI> = _state.asStateFlow()

//    fun navegar(menu: MenuModel) {
//        viewModelScope.launch(Dispatchers.IO) {
//            _state.value = MenuStateUI.Loading
//
//
//            result.fold(
//                onSuccess = {
//                        menu -> _state.value = MenuStateUI.Success( menu )
//                },
//                onFailure = {
//                    val message = "Error al registrar perro"
//
//                    _state.value = MenuStateUI.Error(message = message)
//                }
//            )
//        }
//    }
}
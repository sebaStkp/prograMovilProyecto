package com.ucb.perritos.features.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.perritos.features.login.domain.model.LoginModel
import com.ucb.perritos.features.login.domain.usecase.SetTokenUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel (
    val usecaseSetToken: SetTokenUseCase
): ViewModel() {
    sealed class LoginStateUI{
        object Loading: LoginStateUI()
        class Error(val message: String): LoginStateUI()
        class Success(val data: LoginModel) : LoginStateUI()

    }
//    init {
//        getDollar()
//    }
    private val _state = MutableStateFlow<LoginStateUI>(LoginStateUI.Loading)
    val state: StateFlow<LoginStateUI> = _state.asStateFlow()



    fun setToken(nickname: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = LoginStateUI.Loading
            val result = usecaseSetToken.invoke(nickname)


            result.fold(
                onSuccess = {
                        user -> _state.value = LoginStateUI.Success( user )
                },
                onFailure = {
                    val message = "error desconocido"

                    _state.value = LoginStateUI.Error(message = message)
                }
            )
        }
    }

}
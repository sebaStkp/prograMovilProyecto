package com.ucb.perritos.features.registroMascota.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.develoop.logs.LogApi
import com.google.protobuf.ByteString
import com.ucb.perritos.features.logs.data.datasource.LogsRemoteDataSource
import com.ucb.perritos.features.perfilPerro.domain.usecase.EstablecerPerfilActualUseCase
import com.ucb.perritos.features.registroMascota.domain.model.RegistroPerroModel
import com.ucb.perritos.features.registroMascota.domain.usecase.RegistrarPerroUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.invoke

class RegistroPerroViewModel(
    private val registrarPerroUseCase: RegistrarPerroUseCase,
    private val establecerPerfilActualUseCase: EstablecerPerfilActualUseCase
    //val usecase: RegistrarPerroUseCase,
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
            //val result = usecase.invoke(perro)
            val result = registrarPerroUseCase.invoke(perro)

            result.fold(

                onSuccess = { perroGuardado ->
                    establecerPerfilActualUseCase(
                        perroId = 1L,
                        nombre = perroGuardado.nombrePerro.orEmpty(),
                        raza = perroGuardado.raza.orEmpty(),
                        avatarUrl = "https://picsum.photos/200"
                    )


                    _state.value = RegistrarPerroStateUI.Success(perroGuardado)
                },
                onFailure = {
                    val message = "Error al registrar perro"

                    _state.value = RegistrarPerroStateUI.Error(message = message)
                }
            )
        }
    }

}
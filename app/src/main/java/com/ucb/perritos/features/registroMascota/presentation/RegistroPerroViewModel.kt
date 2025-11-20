package com.ucb.perritos.features.registroMascota.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.perritos.features.core.supabase
import com.ucb.perritos.features.perfilPerro.domain.usecase.EstablecerPerfilActualUseCase
import com.ucb.perritos.features.registroMascota.domain.model.RegistroPerroModel
import com.ucb.perritos.features.registroMascota.domain.usecase.RegistrarPerroUseCase
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class PerroRemoto(

    val nombre_perro: String,
    val raza: String,
    val edad: Int,
    val descripcion: String,

    @SerialName("id_usuario")
    val id_isuario: String
)

class RegistroPerroViewModel(
    private val registrarPerroUseCase: RegistrarPerroUseCase,
    private val establecerPerfilActualUseCase: EstablecerPerfilActualUseCase
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

            val result = registrarPerroUseCase.invoke(perro)

            result.fold(
                onSuccess = { perroGuardado ->
                    try {

                        val currentUser = supabase.auth.currentUserOrNull()

                        if (currentUser != null) {

                            val perroParaNube = PerroRemoto(
                                nombre_perro = perroGuardado.nombrePerro ?: "",
                                raza = perroGuardado.raza ?: "",
                                edad = perroGuardado.edad ?: 0,
                                descripcion = perroGuardado.descripcion ?: "",
                                id_isuario = currentUser.id
                            )


                            supabase.from("perritos").insert(perroParaNube)
                            println("Perro guardado en Supabase vinculado al usuario ${currentUser.id}")
                        } else {
                            println("Advertencia: No hay usuario logueado, solo se guardó en local.")
                        }


                        establecerPerfilActualUseCase(
                            perroId = 1L,
                            nombre = perroGuardado.nombrePerro.orEmpty(),
                            raza = perroGuardado.raza.orEmpty(),
                            avatarUrl = "https://picsum.photos/200"
                        )

                        _state.value = RegistrarPerroStateUI.Success(perroGuardado)

                    } catch (e: Exception) {
                        println("Error guardando en Supabase: ${e.message}")

                        _state.value = RegistrarPerroStateUI.Success(perroGuardado)
                    }
                },
                onFailure = {
                    val message = "Error al registrar perro en local"
                    _state.value = RegistrarPerroStateUI.Error(message = message)
                }
            )
        }
    }
}
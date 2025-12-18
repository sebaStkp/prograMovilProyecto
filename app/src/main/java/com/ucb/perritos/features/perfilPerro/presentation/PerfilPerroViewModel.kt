package com.ucb.perritos.features.perfilPerro.presentation

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.perritos.features.perfilPerro.domain.model.FotoPerroModel

import com.ucb.perritos.features.perfilPerro.domain.model.PerfilPerroModel
import com.ucb.perritos.features.perfilPerro.domain.repository.IFotoPerroRepository
import com.ucb.perritos.features.perfilPerro.domain.repository.IPerfilPerroRepository
import com.ucb.perritos.features.perfilPerro.domain.usecase.ObtenerPerfilPerroUseCase
import com.ucb.perritos.features.perfilPerro.domain.usecase.SubirYGuardarFotoPerroUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PerfilPerroUiState(
    val perfil: PerfilPerroModel? = null,
    val loading: Boolean = true,
    val fotos: List<FotoPerroModel> = emptyList(),
    val error: String? = null
)

class PerfilPerroViewModel(
    private val getPerfil: ObtenerPerfilPerroUseCase,
    private val repo: IPerfilPerroRepository,
    private val fotoRepo: IFotoPerroRepository,
    private val subirYGuardarFoto: SubirYGuardarFotoPerroUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PerfilPerroUiState())
    val state: StateFlow<PerfilPerroUiState> = _state.asStateFlow()

//    fun init(perroId: Long) {
//        viewModelScope.launch {
//            getPerfil(perroId).collect { perfil ->
//                _state.update { it.copy(perfil = perfil, loading = false) }
//            }
//        }
//    }
    fun init(perroId: Long) {
        viewModelScope.launch {
        _state.update { it.copy(loading = true, error = null) }

        // ✅ Usar repo (tu variable original)
        repo.syncPerfilDesdeSupabase(perroId)

        combine(
            getPerfil(perroId),
            fotoRepo.observarFotos(perroId)
        ) { perfil, fotos ->
            perfil to fotos
        }.collect { (perfil, fotos) ->
            _state.update {
                it.copy(
                    perfil = perfil,
                    fotos = fotos,
                    loading = false
                )
            }
        }
    }
    }

    fun agregarFoto(perroId: Long, imageUri: Uri) {
        viewModelScope.launch {
            try {
                _state.update { it.copy(loading = true, error = null) }
                subirYGuardarFoto(perroId, imageUri)
                _state.update { it.copy(loading = false) }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        loading = false,
                        error = e.message ?: "Error subiendo foto"
                    )
                }
            }
        }
    }

}
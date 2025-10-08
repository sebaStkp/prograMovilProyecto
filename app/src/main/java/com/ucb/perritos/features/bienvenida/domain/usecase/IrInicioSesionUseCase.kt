package com.ucb.perritos.features.bienvenida.domain.usecase

import com.ucb.perritos.navigation.NavigationViewModel
import com.ucb.perritos.navigation.Screen

class IrInicioSesionUseCase(
    private val navigationViewModel: NavigationViewModel
) {
    fun invoke() {
        navigationViewModel.navigateTo(Screen.Login.route)
    }
}

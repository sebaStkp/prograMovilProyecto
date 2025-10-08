package com.ucb.perritos.navigation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

enum class NavigationOptions {
    DEFAULT,
    CLEAR_BACK_STACK,
    REPLACE_HOME
}
class NavigationViewModel : ViewModel() {

    sealed class NavigationCommand {
        data class NavigateTo(val route: String, val options: NavigationOptions = NavigationOptions.DEFAULT) : NavigationCommand()
        object PopBackStack : NavigationCommand()
    }
    private val _navigationCommand = MutableSharedFlow<NavigationCommand>()
    val navigationCommand = _navigationCommand.asSharedFlow()

    fun navigateTo(route: String, options: NavigationOptions = NavigationOptions.DEFAULT) {
        viewModelScope.launch {
            _navigationCommand.emit(NavigationCommand.NavigateTo(route, options))
        }
    }

    fun popBackStack() {
        viewModelScope.launch {
            _navigationCommand.emit(NavigationCommand.PopBackStack)
        }
    }

//    fun handleDeepLink(intent: android.content.Intent?) {
//        viewModelScope.launch {
//            try {
//                // DEBUG: Log el intent recibido
//                Log.d("NavigationViewModel", "Intent recibido: ${intent?.extras?.keySet()}")
//
//                intent?.extras?.keySet()?.forEach { key ->
//                    Log.d("NavigationViewModel", "Extra: $key = ${intent.getStringExtra(key)}")
//                }
//
//                when {
//                    intent?.hasExtra("navigateTo") == true -> {
//                        val destination = intent.getStringExtra("navigateTo")
//                        Log.d("NavigationViewModel", "Procesando navigateTo: $destination")
//                        handleNavigationDestination(destination)
//                    }
//                    intent?.action == android.content.Intent.ACTION_VIEW -> {
//                        Log.d("NavigationViewModel", "Procesando ACTION_VIEW: ${intent.data}")
//                        handleUriDeepLink(intent.data)
//                    }
//                    intent?.hasExtra("click_action") == true -> {
//                        val clickAction = intent.getStringExtra("click_action")
//                        Log.d("NavigationViewModel", "Procesando click_action: $clickAction")
//                        handleClickAction(clickAction)
//                    }
//                    else -> {
//                        Log.d("NavigationViewModel", "Navegación por defecto a Dollar")
//                        navigateTo(Screen.Bienvenida.route, NavigationOptions.CLEAR_BACK_STACK)
//                    }
//                }
//            } catch (e: Exception) {
//                Log.e("NavigationViewModel", "Error en handleDeepLink", e)
//                navigateTo(Screen.Bienvenida.route, NavigationOptions.CLEAR_BACK_STACK)
//            }
//        }
//    }
//
//    private fun handleClickAction(clickAction: String?) {
//        when (clickAction) {
//            "OPEN_PROFILE" -> navigateTo(Screen.Profile.route, NavigationOptions.REPLACE_HOME)
//            "OPEN_MOVIES" -> navigateTo(Screen.PopularMovies.route, NavigationOptions.REPLACE_HOME)
//            "OPEN_DOLLAR" -> navigateTo(Screen.Dollar.route, NavigationOptions.CLEAR_BACK_STACK)
//            else -> navigateTo(Screen.Dollar.route, NavigationOptions.CLEAR_BACK_STACK)
//        }
//    }

//    private fun handleUriDeepLink(uri: android.net.Uri?) {
//        when (uri?.host) {
//            "profile" -> {
//                navigateTo(Screen.Profile.route, NavigationOptions.REPLACE_HOME)
//            }
//            "movies" -> {
//                navigateTo(Screen.PopularMovies.route, NavigationOptions.REPLACE_HOME)
//            }
//            "dollar" -> {
//                navigateTo(Screen.Dollar.route, NavigationOptions.CLEAR_BACK_STACK)
//            }
//            "github" -> {
//                navigateTo(Screen.Github.route, NavigationOptions.REPLACE_HOME)
//            }
//            "card" -> {
//                navigateTo(Screen.CardExamples.route, NavigationOptions.REPLACE_HOME)
//            }
//            else -> {
//                navigateTo(Screen.Dollar.route, NavigationOptions.CLEAR_BACK_STACK)
//            }
//        }
//    }

//    private fun handleNavigationDestination(destination: String?) {
//        when (destination?.uppercase()) {
//            "PROFILE" -> navigateTo(Screen.Profile.route, NavigationOptions.REPLACE_HOME)
//            "MOVIES" -> navigateTo(Screen.PopularMovies.route, NavigationOptions.REPLACE_HOME)
//            "DOLLAR" -> navigateTo(Screen.Dollar.route, NavigationOptions.CLEAR_BACK_STACK)
//            "GITHUB" -> navigateTo(Screen.Github.route, NavigationOptions.REPLACE_HOME)
//            "CARD" -> navigateTo(Screen.CardExamples.route, NavigationOptions.REPLACE_HOME)
//            "HOME" -> navigateTo(Screen.Home.route, NavigationOptions.REPLACE_HOME)
//            else -> navigateTo(Screen.Dollar.route, NavigationOptions.CLEAR_BACK_STACK)
//        }
//    }
}
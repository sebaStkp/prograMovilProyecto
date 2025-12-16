package com.ucb.perritos

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.ucb.perritos.features.header.AppHeader
import com.ucb.perritos.navigation.AppNavigation
import com.ucb.perritos.navigation.NavigationViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel // Importante: Usar el de Koin

// ❌ BORRA EL "val supabase = ..." QUE TENÍAS AQUÍ. YA ESTÁ EN KOIN.

class MainActivity : ComponentActivity() {

    // Usamos la inyección de Koin ('by viewModel') en lugar de la nativa ('by viewModels')
    private val navigationViewModel: NavigationViewModel by viewModel()

    @OptIn(ExperimentalPermissionsApi::class) // Necesario para los permisos
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            // ✅ CORRECTO: Las funciones Composable van DENTRO de setContent

            // 1. Manejo de Permiso de Notificaciones (Android 13+)
            val permissionState = rememberPermissionState(Manifest.permission.POST_NOTIFICATIONS)

            LaunchedEffect(Unit) {
                if (!permissionState.status.isGranted) {
                    permissionState.launchPermissionRequest()
                }
            }

            // 2. Interfaz de Usuario
            Scaffold(
                content = { paddingValues ->
                    Column(
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxSize()
                    ) {
                        AppHeader()

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .navigationBarsPadding()
                        ) {
                            // Pasamos el ViewModel que inyectamos arriba
                            AppNavigation(navigationViewModel)
                        }
                    }
                },
                contentWindowInsets = WindowInsets(bottom = 0.dp)
            )
        }
    }
}
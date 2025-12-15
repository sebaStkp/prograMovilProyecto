package com.ucb.perritos

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ucb.perritos.features.header.AppHeader
//import com.ucb.perritos.features.menu.presentation.MenuScreen

import com.ucb.perritos.navigation.AppNavigation
import com.ucb.perritos.navigation.NavigationViewModel
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.flow.distinctUntilChanged





val supabase = createSupabaseClient(
    supabaseUrl = "https://efxdadwzzvsuvtefmlbm.supabase.co",
    supabaseKey = BuildConfig.SUPABASE_KEY
) {
    install(Postgrest)
}
class MainActivity : ComponentActivity() {
    private val navigationViewModel: NavigationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Scaffold(
//                bottomBar = {
//                    MenuScreen()
//                },
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
                            AppNavigation(navigationViewModel)
                        }
                    }
                },
                contentWindowInsets = WindowInsets(bottom = 0.dp)
            )
        }

    }
}






//class MainActivity : ComponentActivity() {


//



//






//




//                        Log.d("MainActivity", "Nuevo intent recibido: ${intent?.action}")
//                        navigationViewModel.handleDeepLink(intent)
//                    }
//            }
//
//            AppNavigation(navigationViewModel)
//        }
//    }
//
//    override fun onNewIntent(intent: Intent) {
//        super.onNewIntent(intent)
//        Log.d("MainActivity", "onNewIntent llamado")
//
//        this.intent = intent
//        currentIntent = intent
//
//        navigationViewModel.handleDeepLink(intent)
//    }
//}

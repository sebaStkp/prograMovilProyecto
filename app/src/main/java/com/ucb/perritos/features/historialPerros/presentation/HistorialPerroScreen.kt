package com.ucb.perritos.features.historialPerros.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ucb.perritos.features.historialPerros.domain.model.HistorialUbicacionModel

import org.koin.androidx.compose.koinViewModel

private val OrangePrimary = Color(0xFFF89A22)
private val TextBlueGray = Color(0xFF6A8693)

@Composable
fun HistorialPerroScreen(
    perroId: Long,
    modifier: Modifier = Modifier,
    vm: HistorialPerroViewModel = koinViewModel()
) {
    val uiState by vm.state.collectAsState()


    LaunchedEffect(perroId) {
        vm.cargarHistorial(perroId)
    }

    Scaffold(
        topBar = {
            HeaderHistorial()
        }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
        ) {
            when (val state = uiState) {
                is HistorialPerroViewModel.HistorialStateUI.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = OrangePrimary)
                    }
                }

                is HistorialPerroViewModel.HistorialStateUI.Error -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = state.message, color = Color.Red, modifier = Modifier.padding(16.dp))
                        Button(onClick = { vm.cargarHistorial(perroId) }, colors = ButtonDefaults.buttonColors(containerColor = OrangePrimary)) {
                            Text("Reintentar")
                        }
                    }
                }

                is HistorialPerroViewModel.HistorialStateUI.Success -> {
                    if (state.lista.isEmpty()) {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("No hay historial registrado", color = TextBlueGray)
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(state.lista) { item ->
                                CardHistorialItem(item)
                            }
                        }
                    }
                }
                else -> Unit
            }
        }
    }
}

@Composable
fun HeaderHistorial() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = OrangePrimary.copy(alpha = 0.1f),
                modifier = Modifier.size(40.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.History,
                        contentDescription = null,
                        tint = OrangePrimary
                    )
                }
            }
            Spacer(Modifier.width(12.dp))
            Text(
                text = "Historial de ubicación",
                color = OrangePrimary,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(Modifier.height(12.dp))
        Divider(color = Color(0xFFF0F0F0), thickness = 1.dp)
    }
}

@Composable
fun CardHistorialItem(item: HistorialUbicacionModel) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        border = BorderStroke(1.5.dp, OrangePrimary)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {



            val hora = item.fecha_hora?.let { if (it.length >= 16) it.substring(11, 16) else it } ?: "--:--"
            InfoRowHistorial(Icons.Default.AccessTime, "Hora:", "$hora")

            Spacer(Modifier.height(12.dp))


            val fecha = item.fecha_hora?.let { if (it.length >= 10) it.substring(0, 10).split("-").reversed().joinToString("/") else it } ?: "--/--/--"
            InfoRowHistorial(Icons.Default.CalendarToday, "Fecha:", fecha)
        }
    }
}

@Composable
fun InfoRowHistorial(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = OrangePrimary,
            modifier = Modifier.size(20.dp)
        )
        Spacer(Modifier.width(12.dp))
        Text(
            text = label,
            color = TextBlueGray,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            color = Color.Gray,
            fontSize = 15.sp
        )
    }
}
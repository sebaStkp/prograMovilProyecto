package com.ucb.perritos.features.menu.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ucb.perritos.R
import com.ucb.perritos.navigation.NavigationOptions
import com.ucb.perritos.navigation.NavigationViewModel
import com.ucb.perritos.navigation.Screen

// Este componente ahora actuará como el contenedor principal de tu diseño personalizado
@Composable
fun MenuScreen(
    navController: NavController,
    navigationViewModel: NavigationViewModel
) {
    // Obtenemos la ruta actual para saber qué icono pintar
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    BottomNavigationBar(
        currentRoute = currentRoute,
        onNavigate = { route ->
            // Evitamos recargar la misma pantalla si ya estamos en ella
            if (currentRoute != route) {
                // Usamos REPLACE_HOME para que no se acumulen pantallas infinitamente
                navigationViewModel.navigateTo(route, NavigationOptions.REPLACE_HOME)
            }
        },
        onMapClick = {
            navigationViewModel.navigateTo(Screen.BuscarPerro.route)
        }
    )
}

@Composable
fun BottomNavigationBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit,
    onMapClick: () -> Unit
) {
    val inactiveColor = Color(0xFF9E9E9E)
    // El color activo se maneja internamente en el NavItem, pero lo definimos aquí por consistencia
    val activeGradient = Brush.linearGradient(
        colors = listOf(
            Color(0xFFFF6F00),
            Color(0xFFFF9800)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp) // Altura total considerando el botón flotante
    ) {

        // 1. Fondo Blanco Curvo
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .align(Alignment.BottomCenter)
                .shadow(
                    elevation = 16.dp,
                    shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                    spotColor = Color(0x20000000),
                    ambientColor = Color(0x10000000)
                )
                .background(
                    Color.White,
                    RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                )
        )

        // 2. Fila de Iconos
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp) // Reduje un poco el padding para que quepan bien
                .align(Alignment.BottomCenter)
                .padding(bottom = 10.dp), // Ajuste visual
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            // IZQUIERDA
//            NavItem(
//                label = "Seguro",
//                color = inactiveColor,
//                isActive = currentRoute == "ruta_seguro", // Reemplaza con Screen.Seguro.route si existe
//                icon = Icons.Default.Shield,
//                onClick = { /* onNavigate(Screen.Seguro.route) */ }
//            )
//            NavItem(
//                label = "Citas",
//                color = inactiveColor,
//                isActive = currentRoute == "ruta_citas",
//                icon = Icons.Default.Event,
//                onClick = { /* onNavigate(Screen.Citas.route) */ }
//            )

            NavItem(
                label = stringResource(id = R.string.menu_navitem_label_mascotas),
                color = inactiveColor,
                isActive = currentRoute == Screen.MisPerros.route,
                icon = Icons.Default.Pets,
                onClick = { onNavigate(Screen.MisPerros.route) }
            )

            // ESPACIO CENTRAL (Hueco para el botón flotante)
            Spacer(modifier = Modifier.width(60.dp))

            // DERECHA
//            NavItem(
//                label = "Mascotas",
//                color = inactiveColor,
//                isActive = currentRoute == Screen.MisPerros.route,
//                icon = Icons.Default.Pets,
//                onClick = { onNavigate(Screen.MisPerros.route) }
//            )
            NavItem(
                label = stringResource(id = R.string.menu_navitem_label_perfil),
                color = inactiveColor,
                isActive = currentRoute == Screen.PerfilUsuario.route,
                icon = Icons.Default.Person,
                onClick = { onNavigate(Screen.PerfilUsuario.route) }
            )
        }

        // 3. Botón Flotante Central (Mapa)
        Box(
            modifier = Modifier
                .size(65.dp)
                .align(Alignment.BottomCenter)
                .offset(y = (-35).dp) // Sube el botón
                .shadow(
                    elevation = 10.dp,
                    shape = CircleShape,
                    spotColor = Color(0x40FF6F00)
                )
                .clip(CircleShape)
                .background(activeGradient)
                .clickable { onMapClick() },
            contentAlignment = Alignment.Center
        ) {
            // Círculo interior translúcido para efecto visual
            Box(
                modifier = Modifier
                    .size(55.dp)
                    .background(Color.White.copy(alpha = 0.2f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = stringResource(id = R.string.menu_navitem_cd_mapa),
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}

@Composable
fun NavItem(
    label: String,
    color: Color,
    isActive: Boolean,
    icon: ImageVector,
    onClick: () -> Unit
) {
    // Definimos el color aquí para usarlo dinámicamente
    val itemColor = if (isActive) Color(0xFFFF6F00) else color

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .width(60.dp) // Ancho fijo para facilitar el click
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(vertical = 4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = itemColor,
            modifier = Modifier.size(26.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = if (isActive) FontWeight.Bold else FontWeight.Medium,
            color = itemColor,
            textAlign = TextAlign.Center
        )

        // Indicador de punto debajo si está activo
        if (isActive) {
            Spacer(modifier = Modifier.height(2.dp))
            Box(
                modifier = Modifier
                    .size(4.dp)
                    .background(Color(0xFFFF6F00), CircleShape)
            )
        } else {
            Spacer(modifier = Modifier.height(6.dp)) // Espacio invisible para mantener altura
        }
    }
}
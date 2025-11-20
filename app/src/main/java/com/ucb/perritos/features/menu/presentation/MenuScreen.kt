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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MenuScreen() {
    BottomNavigationBar()
}

@Composable
fun BottomNavigationBar() {

    val inactiveColor = Color(0xFF9E9E9E)
    val activeColor = Color(0xFFFF6F00)


    val activeGradient = Brush.linearGradient(
        colors = listOf(
            Color(0xFFFF6F00),
            Color(0xFFFF9800)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {


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


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            NavItem(
                label = "Seguro",
                color = inactiveColor,
                isActive = false,
                icon = Icons.Default.Shield
            )
            NavItem(
                label = "Citas",
                color = inactiveColor,
                isActive = false,
                icon = Icons.Default.Event
            )


            Spacer(modifier = Modifier.width(80.dp))


            NavItem(
                label = "Mascotas",
                color = inactiveColor,
                isActive = false,
                icon = Icons.Default.Pets
            )
            NavItem(
                label = "Perfil",
                color = inactiveColor,
                isActive = false,
                icon = Icons.Default.Person
            )
        }


        Box(
            modifier = Modifier
                .size(70.dp)
                .align(Alignment.BottomCenter)
                .offset(y = (-40).dp)
                .shadow(
                    elevation = 10.dp,
                    shape = CircleShape,
                    spotColor = Color(0x40FF6F00)
                )
                .clip(CircleShape)
                .background(activeGradient)
                .clickable {

                },
            contentAlignment = Alignment.Center
        ) {

            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(Color.White.copy(alpha = 0.2f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Mapa",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
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
    icon: ImageVector
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.clickable {

        }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (isActive) Color(0xFFFF6F00) else color,
            modifier = Modifier.size(26.dp)
        )

        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = if (isActive) FontWeight.SemiBold else FontWeight.Medium,
            color = if (isActive) Color(0xFFFF6F00) else color,
            textAlign = TextAlign.Center
        )


        if (isActive) {
            Box(
                modifier = Modifier
                    .size(4.dp)
                    .background(Color(0xFFFF6F00), CircleShape)
            )
        } else {

            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}
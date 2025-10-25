package com.ucb.perritos.features.menu.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MenuScreen(
    onShieldClick: () -> Unit = {},
    onCalendarClick: () -> Unit = {},
    onPawClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    BottomNavigationBar(
        onShieldClick = onShieldClick,
        onCalendarClick = onCalendarClick,
        onPawClick = onPawClick,
        onProfileClick = onProfileClick
    )
}

@Composable
fun BottomNavigationBar(
    onShieldClick: () -> Unit = {},
    onCalendarClick: () -> Unit = {},
    onPawClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    val inactiveColor = Color(0xFFBDBDBD)
    val activeColor = Color(0xFFFF9800)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .padding(bottom = 20.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .align(Alignment.BottomCenter)
                .shadow(elevation = 10.dp, shape = RoundedCornerShape(topStart = 20.dp, topEnd = 0.dp))
                .background(Color.White, RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
        )


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            IconButtonEmoji("🛡️", inactiveColor, onClick = onShieldClick)
            IconButtonEmoji("📅", inactiveColor, onClick = onCalendarClick)
            Spacer(modifier = Modifier.width(64.dp))
            IconButtonEmoji("🐾", inactiveColor, onClick = onPawClick)
            IconButtonEmoji("👤", inactiveColor, onClick = onProfileClick)
        }


        Box(
            modifier = Modifier
                .size(64.dp)
                .align(Alignment.BottomCenter)
                .offset(y = (-30).dp)
                .shadow(elevation = 12.dp, shape = CircleShape)
                .background(activeColor, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "📍",
                fontSize = 28.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun IconButtonEmoji(emoji: String, tint: Color, onClick: () -> Unit) {
    Text(
        text = emoji,
        fontSize = 24.sp,
        color = tint,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Normal,
        modifier = Modifier
            .padding(bottom = 12.dp)
            .clickable { onClick() }
    )
}

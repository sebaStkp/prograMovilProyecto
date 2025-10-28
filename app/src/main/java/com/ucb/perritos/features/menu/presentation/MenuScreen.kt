package com.ucb.perritos.features.menu.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
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
            .padding(bottom = 0.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .align(Alignment.BottomCenter)
                .shadow(
                    elevation = 24.dp,
                    shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                    spotColor = Color(0x20000000),
                    ambientColor = Color(0x15000000)
                )
                .background(
                    Color.White,
                    RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                )
        )


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            NavItem("Seguro", inactiveColor, false) { ShieldIcon(inactiveColor) }
            NavItem("Citas", inactiveColor, false) { CalendarIcon(inactiveColor) }
            Spacer(modifier = Modifier.width(90.dp))
            NavItem("Mascotas", inactiveColor, false) { PawIcon(inactiveColor) }
            NavItem("Perfil", inactiveColor, false) { UserIcon(inactiveColor) }
        }


        Box(
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.BottomCenter)
                .offset(y = (-40).dp)
                .shadow(
                    elevation = 24.dp,
                    shape = CircleShape,
                    spotColor = Color(0x40FF6F00),
                    ambientColor = Color(0x30FF6F00)
                )
                .clip(CircleShape)
                .background(activeGradient),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .background(
                        Color.White.copy(alpha = 0.2f),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                MapPinIcon(Color.White)
            }
        }
    }
}

@Composable
fun NavItem(
    label: String,
    color: Color,
    isActive: Boolean,
    icon: @Composable () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Box(
            modifier = Modifier.size(28.dp),
            contentAlignment = Alignment.Center
        ) {
            icon()
        }

        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = if (isActive) FontWeight.SemiBold else FontWeight.Medium,
            color = color,
            textAlign = TextAlign.Center,
            letterSpacing = 0.2.sp
        )

        if (isActive) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(4.dp)
                    .background(color, CircleShape)
            )
        }
    }
}

@Composable
fun ShieldIcon(color: Color) {
    Canvas(modifier = Modifier.size(24.dp)) {
        val path = Path().apply {
            moveTo(size.width * 0.5f, size.height * 0.1f)
            lineTo(size.width * 0.85f, size.height * 0.3f)
            lineTo(size.width * 0.85f, size.height * 0.6f)
            lineTo(size.width * 0.5f, size.height * 0.9f)
            lineTo(size.width * 0.15f, size.height * 0.6f)
            lineTo(size.width * 0.15f, size.height * 0.3f)
            close()
        }
        drawPath(
            path = path,
            color = color,
            style = Stroke(width = 2.5f, cap = StrokeCap.Round)
        )
    }
}

@Composable
fun CalendarIcon(color: Color) {
    Canvas(modifier = Modifier.size(24.dp)) {
        // Rectángulo principal
        drawRoundRect(
            color = color,
            topLeft = Offset(size.width * 0.15f, size.height * 0.25f),
            size = androidx.compose.ui.geometry.Size(
                size.width * 0.7f,
                size.height * 0.65f
            ),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(4.dp.toPx()),
            style = Stroke(width = 2.5f, cap = StrokeCap.Round)
        )
        // Línea superior
        drawLine(
            color = color,
            start = Offset(size.width * 0.15f, size.height * 0.4f),
            end = Offset(size.width * 0.85f, size.height * 0.4f),
            strokeWidth = 2.5f,
            cap = StrokeCap.Round
        )

        drawLine(
            color = color,
            start = Offset(size.width * 0.35f, size.height * 0.15f),
            end = Offset(size.width * 0.35f, size.height * 0.3f),
            strokeWidth = 2.5f,
            cap = StrokeCap.Round
        )
        drawLine(
            color = color,
            start = Offset(size.width * 0.65f, size.height * 0.15f),
            end = Offset(size.width * 0.65f, size.height * 0.3f),
            strokeWidth = 2.5f,
            cap = StrokeCap.Round
        )
    }
}

@Composable
fun MapPinIcon(color: Color) {
    Canvas(modifier = Modifier.size(32.dp)) {
        val path = Path().apply {
            moveTo(size.width * 0.5f, size.height * 0.15f)
            cubicTo(
                size.width * 0.25f, size.height * 0.15f,
                size.width * 0.2f, size.height * 0.35f,
                size.width * 0.2f, size.height * 0.45f
            )
            cubicTo(
                size.width * 0.2f, size.height * 0.65f,
                size.width * 0.5f, size.height * 0.85f,
                size.width * 0.5f, size.height * 0.85f
            )
            cubicTo(
                size.width * 0.5f, size.height * 0.85f,
                size.width * 0.8f, size.height * 0.65f,
                size.width * 0.8f, size.height * 0.45f
            )
            cubicTo(
                size.width * 0.8f, size.height * 0.35f,
                size.width * 0.75f, size.height * 0.15f,
                size.width * 0.5f, size.height * 0.15f
            )
            close()
        }
        drawPath(
            path = path,
            color = color,
            style = Stroke(width = 3f, cap = StrokeCap.Round)
        )
        // Círculo interior
        drawCircle(
            color = color,
            radius = size.width * 0.12f,
            center = Offset(size.width * 0.5f, size.height * 0.42f),
            style = Stroke(width = 3f)
        )
    }
}

@Composable
fun PawIcon(color: Color) {
    Canvas(modifier = Modifier.size(24.dp)) {
        // Almohadilla principal
        drawCircle(
            color = color,
            radius = size.width * 0.18f,
            center = Offset(size.width * 0.5f, size.height * 0.65f)
        )
        // Dedos
        drawCircle(
            color = color,
            radius = size.width * 0.12f,
            center = Offset(size.width * 0.3f, size.height * 0.35f)
        )
        drawCircle(
            color = color,
            radius = size.width * 0.12f,
            center = Offset(size.width * 0.5f, size.height * 0.25f)
        )
        drawCircle(
            color = color,
            radius = size.width * 0.12f,
            center = Offset(size.width * 0.7f, size.height * 0.35f)
        )
        drawCircle(
            color = color,
            radius = size.width * 0.11f,
            center = Offset(size.width * 0.75f, size.height * 0.55f)
        )
    }
}

@Composable
fun UserIcon(color: Color) {
    Canvas(modifier = Modifier.size(24.dp)) {
        // Cabeza
        drawCircle(
            color = color,
            radius = size.width * 0.2f,
            center = Offset(size.width * 0.5f, size.height * 0.3f),
            style = Stroke(width = 2.5f, cap = StrokeCap.Round)
        )
        // Cuerpo
        val bodyPath = Path().apply {
            moveTo(size.width * 0.2f, size.height * 0.85f)
            cubicTo(
                size.width * 0.2f, size.height * 0.6f,
                size.width * 0.3f, size.height * 0.55f,
                size.width * 0.5f, size.height * 0.55f
            )
            cubicTo(
                size.width * 0.7f, size.height * 0.55f,
                size.width * 0.8f, size.height * 0.6f,
                size.width * 0.8f, size.height * 0.85f
            )
        }
        drawPath(
            path = bodyPath,
            color = color,
            style = Stroke(width = 2.5f, cap = StrokeCap.Round)
        )
    }
}
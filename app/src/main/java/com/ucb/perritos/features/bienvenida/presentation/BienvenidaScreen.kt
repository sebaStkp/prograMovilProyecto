package com.ucb.perritos.features.bienvenida.presentation


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.ucb.perritos.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun BienvenidaScreen(
    vm: BienvenidaViewModel = koinViewModel(),
) {
    val state by vm.state.collectAsState()

    LaunchedEffect(state) {
        when (state) {
            BienvenidaViewModel.BienvenidaUIState.Init -> {

            }
            is BienvenidaViewModel.BienvenidaUIState.Error -> {

            }
            else -> Unit
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AsyncImage(
                model = R.drawable.icono_bienvenida,
                contentDescription = "Ubicación de mascotas",
                modifier = Modifier
                    .width(200.dp)
                    .height(140.dp)

            )

            Spacer(modifier = Modifier.height(48.dp))


            OutlinedButton(
                onClick = {  },
                modifier = Modifier
                    .width(190.dp)
                    .height(44.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFFF5A623),
                    containerColor = Color.White
                ),
                border = BorderStroke(1.dp, Color(0xFFF5A623))
            ) {
                Text(
                    text = "Registrarse",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))


            val annotatedText = buildAnnotatedString {
                append("Si ya tiene una cuenta porfavor ")
                withStyle(style = SpanStyle(color = Color.Blue)) {
                    append("inicie sesión")
                }
            }

            Text(
                text = annotatedText,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .clickable { vm.onLoginClick() }
                    .padding(16.dp)
            )

        }
    }
}

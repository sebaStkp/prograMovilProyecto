package com.ucb.perritos.features.bienvenida.presentation



import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
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
    navigateLogin: () -> Unit,
    navigateRegistroUsuario: () -> Unit
) {
    val state by vm.state.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
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


            Button(
                onClick = {navigateRegistroUsuario()},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF5A623),
                    contentColor = Color.White
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    text = stringResource(id = R.string.bienvenida_name),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))


            val annotatedText = buildAnnotatedString {
                append(stringResource(id = R.string.registrarse_inicio_sesion))
                append(" ")
                withStyle(style = SpanStyle(color = Color.Blue)) {
                    append(stringResource(id = R.string.bienvenida_iniciar_sesion))

                }
            }

            Text(
                text = annotatedText,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .clickable { navigateLogin() }
                    .padding(16.dp)
            )

        }
    }
}
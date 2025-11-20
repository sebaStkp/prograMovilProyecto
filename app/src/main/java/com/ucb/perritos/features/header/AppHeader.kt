package com.ucb.perritos.features.header
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ucb.perritos.R


private val OrangePrimary = Color(0xFFF89A22)
private val TextBlueGray = Color(0xFF6A8693)
private val HeaderGray = Color(0xFFF2F2F2)

@Composable
fun AppHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(115.dp)
    ) {

        Canvas(modifier = Modifier.fillMaxSize()) {
            val path = Path().apply {
                val w = size.width
                val h = size.height


                moveTo(0f, 0f)

                lineTo(0f, h * 0.80f)

                cubicTo(
                    w * 0.40f, h * 1.15f,
                    w * 0.75f, h * 0.60f,
                    w, h * 0.85f
                )


                lineTo(w, 0f)
                close()
            }
            drawPath(path = path, color = HeaderGray)
        }

        Row(
            modifier = Modifier
                .padding(top = 40.dp, start = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.logo_perrito),
                contentDescription = "Logo",
                tint = OrangePrimary,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))


            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(color = TextBlueGray, fontSize = 24.sp)) {
                        append("Find your ")
                    }
                    withStyle(SpanStyle(color = OrangePrimary, fontSize = 24.sp, fontWeight = FontWeight.Bold)) {
                        append("dog")
                    }
                }
            )
        }
    }
}
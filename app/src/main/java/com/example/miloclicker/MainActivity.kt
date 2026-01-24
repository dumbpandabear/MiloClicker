package com.example.miloclicker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import kotlin.random.Random
import com.example.miloclicker.ui.theme.MiloClickerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiloClickerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CookieClickerGame(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun CookieClickerGame(modifier: Modifier = Modifier) {
    val clickCount = remember { mutableStateOf(0) }
    val targetRotation = remember { mutableStateOf(0f) }
    // Size grows with each click: starts at 150dp, increases by 2dp per click
    val imageSize = 150.dp + (clickCount.value * 2).dp

    // Animate the rotation
    val rotation = animateFloatAsState(
        targetValue = targetRotation.value,
        animationSpec = tween(durationMillis = 600)
    ).value

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .clickable {
                clickCount.value++
                // 30% chance to spin on each click
                if (Random.nextInt(100) < 30) {
                    targetRotation.value += 360f
                }
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Milo image that grows with each click and spins randomly
        Image(
            painter = painterResource(R.drawable.milo),
            contentDescription = "Milo",
            modifier = Modifier
                .size(imageSize)
                .padding(16.dp)
                .graphicsLayer {
                    rotationZ = rotation
                },
            contentScale = ContentScale.Fit
        )

        Text(
            text = "${clickCount.value}",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF795548),
            modifier = Modifier.padding(top = 32.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CookieClickerPreview() {
    MiloClickerTheme {
        CookieClickerGame()
    }
}
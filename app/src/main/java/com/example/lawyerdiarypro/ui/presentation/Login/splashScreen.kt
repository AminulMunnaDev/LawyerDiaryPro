package com.example.lawyerdiarypro.ui.presentation.Login

import com.example.lawyerdiarypro.ui.Screen
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.lawyerdiarypro.ui.theme.Amber
import com.example.lawyerdiarypro.ui.theme.DeepBlue
import com.example.lawyerdiarypro.ui.theme.Emerald
import com.example.lawyerdiarypro.ui.theme.SlateBlue
import com.example.lawyerdiarypro.ui.theme.SurfaceDark
import com.example.lawyerdiarypro.ui.theme.White
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    val infiniteTransition = rememberInfiniteTransition(label = "SplashAnimation")

    // Enhanced scale animation with more dynamic range
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "Scale"
    )

    // Smoother alpha animation
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "Alpha"
    )

    // Add rotation animation for more visual interest
    val rotation by infiniteTransition.animateFloat(
        initialValue = -5f,
        targetValue = 5f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "Rotation"
    )

    // Add subtle shadow animation
    val shadowElevation by infiniteTransition.animateFloat(
        initialValue = 4f,
        targetValue = 12f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "Shadow"
    )

    LaunchedEffect(key1 = true) {
        delay(2500)
        navController.navigate(Screen.Welcome.route) {
            popUpTo(Screen.Splash.route) { inclusive = true }
        }
    }

    // Professional gradient background using your color palette
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        DeepBlue,           // Professional deep blue
                        SlateBlue,           // Modern calm blue
                        SurfaceDark          // Dark surface for depth
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(1000f, 1000f)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Icon with enhanced animations
            Icon(
                imageVector = Icons.Default.Gavel,
                contentDescription = "App Logo",
                tint = White,
                modifier = Modifier
                    .size(120.dp)
                    .scale(scale)
                    .alpha(alpha)
                    .shadow(
                        elevation = shadowElevation.dp,
                        shape = RoundedCornerShape(20.dp),
                        clip = false
                    )
                    .rotate(rotation)  // Add subtle rotation
            )

            Spacer(modifier = Modifier.height(32.dp))

            // App name with gradient text
            Text(
                text = "Lawyer Diary Pro",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                fontSize = 36.sp,
                letterSpacing = 0.5.sp,
                modifier = Modifier.alpha(alpha),
                color = White
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Decorative line with animation
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(2.dp)
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                Amber.copy(alpha = 0.3f),
                                Emerald,
                                Amber.copy(alpha = 0.3f)
                            )
                        )
                    )
                    .scale(scaleX = alpha, scaleY = 1f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Tagline with subtle animation
            Text(
                text = "Your Professional Case Manager",
                style = MaterialTheme.typography.bodyLarge,
                color = White.copy(alpha = 0.9f),
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                letterSpacing = 0.3.sp,
                modifier = Modifier
                    .alpha(alpha)
                    .padding(horizontal = 32.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Animated loading dots
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                repeat(3) { index ->
                    val dotDelay = index * 200
                    val dotAlpha by animateFloatAsState(
                        targetValue = if ((System.currentTimeMillis() / 500) % 2 == 0L) 1f else 0.3f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(500, delayMillis = dotDelay, easing = FastOutSlowInEasing),
                            repeatMode = RepeatMode.Reverse
                        ), label = "DotAlpha"
                    )

                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .padding(horizontal = 4.dp)
                            .background(
                                color = Emerald.copy(alpha = dotAlpha),
                                shape = CircleShape
                            )
                    )
                }
            }
        }
    }
}
package com.example.lawyerdiarypro.ui.presentation.Login

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.lawyerdiarypro.ui.Screen
import com.example.lawyerdiarypro.ui.presentation.custom.SetStatusBarColor
import com.example.lawyerdiarypro.ui.theme.Amber
import com.example.lawyerdiarypro.ui.theme.DeepBlue
import com.example.lawyerdiarypro.ui.theme.Emerald
import com.example.lawyerdiarypro.ui.theme.InfoBlue
import com.example.lawyerdiarypro.ui.theme.SlateBlue
import com.example.lawyerdiarypro.ui.theme.SuccessGreen
import com.example.lawyerdiarypro.ui.theme.SurfaceLight
import com.example.lawyerdiarypro.ui.theme.TextMuted
import com.example.lawyerdiarypro.ui.theme.TextSecondary
import com.example.lawyerdiarypro.ui.theme.White
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun WelcomeScreen(navController: NavHostController) {

    SetStatusBarColor(
        statusBarColor = Color.Transparent,
        darkIcons = true
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        SurfaceLight,
                        White
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(0f, 600f)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // Logo
            Surface(
                color = DeepBlue.copy(alpha = 0.1f),
                shape = CircleShape,
                modifier = Modifier.size(100.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Balance,
                    contentDescription = "App Logo",
                    tint = DeepBlue,
                    modifier = Modifier
                        .size(60.dp)
                        .padding(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Title
            Text(
                text = "Lawyer Diary Pro",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                fontSize = 28.sp,
                letterSpacing = 0.5.sp,
                modifier = Modifier.fillMaxWidth(),
                color = DeepBlue
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Decorative Line
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.25f)
                    .height(2.dp)
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                Emerald.copy(alpha = 0.3f),
                                DeepBlue,
                                Emerald.copy(alpha = 0.3f)
                            )
                        )
                    )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Subtitle
            Text(
                text = "Your Professional Case Management Solution",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary,
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                letterSpacing = 0.3.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Features
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                FeatureItem(Icons.AutoMirrored.Filled.Assignment, "Manage Cases Efficiently", DeepBlue, Emerald, smaller = true)
                FeatureItem(Icons.Default.People, "Track Client Information", SlateBlue, InfoBlue, smaller = true)
                FeatureItem(Icons.Default.Event, "Schedule Appointments", DeepBlue, Amber, smaller = true)
                FeatureItem(Icons.Default.Description, "Document Management", SlateBlue, SuccessGreen, smaller = true)
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Buttons
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {

                Button(
                    onClick = { navController.navigate(Screen.SignUp.route) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DeepBlue,
                        contentColor = White
                    )
                ) {
                    Text(
                        "Get Started",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp
                    )
                }

                OutlinedButton(
                    onClick = { navController.navigate(Screen.SignIn.route) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.5.dp, SlateBlue),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = SlateBlue
                    )
                ) {
                    Text(
                        "Sign In",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Footer
            Text(
                text = "By continuing, you agree to our Terms of Service",
                style = MaterialTheme.typography.labelSmall,
                color = TextMuted,
                textAlign = TextAlign.Center,
                fontSize = 11.sp,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

// Updated FeatureItem composable with centered text
@Composable
fun FeatureItem(
    icon: ImageVector,
    text: String,
    iconColor: Color,
    textColor: Color,
    smaller: Boolean = false
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start, // Keep items left-aligned within the row
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = if (smaller) 12.dp else 16.dp)
    ) {
        Surface(
            color = iconColor.copy(alpha = 0.1f),
            shape = CircleShape,
            modifier = Modifier
                .size(if (smaller) 36.dp else 44.dp)
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier
                    .size(if (smaller) 20.dp else 24.dp)
                    .padding(if (smaller) 8.dp else 10.dp)
            )
        }

        Spacer(modifier = Modifier.width(if (smaller) 12.dp else 16.dp))

        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = textColor,
            fontSize = if (smaller) 14.sp else 16.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Start, // Keep text left-aligned within the feature item
            modifier = Modifier.weight(1f)
        )
    }
}


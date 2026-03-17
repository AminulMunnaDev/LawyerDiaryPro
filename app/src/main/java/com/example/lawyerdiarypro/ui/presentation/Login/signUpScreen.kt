package com.example.lawyerdiarypro.ui.presentation.Login

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.lawyerdiarypro.ui.Screen
import com.example.lawyerdiarypro.ui.presentation.custom.SetStatusBarColor
import com.example.lawyerdiarypro.ui.theme.BorderLight
import com.example.lawyerdiarypro.ui.theme.DeepBlue
import com.example.lawyerdiarypro.ui.theme.Emerald
import com.example.lawyerdiarypro.ui.theme.Rose
import com.example.lawyerdiarypro.ui.theme.SlateBlue
import com.example.lawyerdiarypro.ui.theme.TextMuted
import com.example.lawyerdiarypro.ui.theme.TextPrimary
import com.example.lawyerdiarypro.ui.theme.TextSecondary
import com.example.lawyerdiarypro.ui.theme.White
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(navController: NavHostController) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    val animatedProgress = remember { Animatable(0f) }
    val fieldAnimations = List(4) { remember { Animatable(0f) } }
    val buttonScale = remember { Animatable(0.8f) }

    SetStatusBarColor(
        statusBarColor = Color.Transparent,
        darkIcons = true
    )

    LaunchedEffect(key1 = true) {
        // Main content fade-in
        animatedProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(800, easing = FastOutSlowInEasing)
        )

        // Animate fields sequentially
        fieldAnimations.forEachIndexed { index, animatable ->
            delay(index * 150L)
            animatable.animateTo(
                targetValue = 1f,
                animationSpec = tween(600, easing = FastOutSlowInEasing)
            )
        }

        // Button bounce
        buttonScale.animateTo(
            targetValue = 1f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
    }

    // Scaffold with white background
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier
                            .scale(1f)
                            .animateContentSize()
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = DeepBlue
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        containerColor = Color.White // This sets the background color of the Scaffold
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White) // Also set background here to be safe
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
                .statusBarsPadding()
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // Header with gradient line
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(animatedProgress.value)
            ) {
                Text(
                    text = "Create Account",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = DeepBlue,
                    fontSize = 40.sp,
                    letterSpacing = 0.5.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.2f)
                        .height(4.dp)
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    Emerald,
                                    DeepBlue
                                )
                            )
                        )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Join Lawyer Diary Pro to manage your practice.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextSecondary,
                    fontSize = 16.sp,
                    letterSpacing = 0.3.sp
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Username Field with animation
            Box(
                modifier = Modifier
                    .scale(fieldAnimations[0].value)
                    .alpha(fieldAnimations[0].value)
                    .fillMaxWidth()
            ) {
                SignUpTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = "Full Name",
                    icon = Icons.Default.Person,
                    iconColor = DeepBlue
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Email Field with animation
            Box(
                modifier = Modifier
                    .scale(fieldAnimations[1].value)
                    .alpha(fieldAnimations[1].value)
                    .fillMaxWidth()
            ) {
                SignUpTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Email Address",
                    icon = Icons.Default.Email,
                    keyboardType = KeyboardType.Email,
                    iconColor = SlateBlue
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Password Field with animation
            Box(
                modifier = Modifier
                    .scale(fieldAnimations[2].value)
                    .alpha(fieldAnimations[2].value)
                    .fillMaxWidth()
            ) {
                SignUpTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = "Password",
                    icon = Icons.Default.Lock,
                    isPassword = true,
                    isVisible = passwordVisible,
                    onVisibilityChange = { passwordVisible = !passwordVisible },
                    iconColor = DeepBlue
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Confirm Password Field with animation
            Box(
                modifier = Modifier
                    .scale(fieldAnimations[3].value)
                    .alpha(fieldAnimations[3].value)
                    .fillMaxWidth()
            ) {
                val passwordsMatch = password == confirmPassword || confirmPassword.isEmpty()
                SignUpTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = "Confirm Password",
                    icon = Icons.Default.Lock,
                    isPassword = true,
                    isVisible = confirmPasswordVisible,
                    onVisibilityChange = { confirmPasswordVisible = !confirmPasswordVisible },
                    isError = !passwordsMatch && confirmPassword.isNotEmpty(),
                    supportingText = if (!passwordsMatch && confirmPassword.isNotEmpty()) "Passwords do not match" else null,
                    iconColor = if (!passwordsMatch && confirmPassword.isNotEmpty()) Rose else SlateBlue
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Password requirements indicator
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(fieldAnimations[3].value)
                    .padding(horizontal = 4.dp)
            ) {
                Text(
                    text = "Password must:",
                    style = MaterialTheme.typography.labelMedium,
                    color = TextSecondary,
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    PasswordRequirement(
                        text = "6+ characters",
                        isValid = password.length >= 6,
                        color = DeepBlue
                    )
                    PasswordRequirement(
                        text = "Match confirmation",
                        isValid = password == confirmPassword && password.isNotEmpty(),
                        color = DeepBlue
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Sign Up Button with animation
            Box(
                modifier = Modifier
                    .scale(buttonScale.value)
                    .fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        // Add ripple effect on click
                        navController.navigate(Screen.Home.route)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    enabled = username.isNotBlank() && email.isNotBlank() &&
                            password.length >= 6 && password == confirmPassword,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DeepBlue,
                        contentColor = White,
                        disabledContainerColor = DeepBlue.copy(alpha = 0.4f),
                        disabledContentColor = White.copy(alpha = 0.7f)
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 2.dp,
                        pressedElevation = 4.dp,
                        disabledElevation = 0.dp
                    )
                ) {
                    Text(
                        "Create Account",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        letterSpacing = 0.5.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Sign In Link
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(fieldAnimations[3].value),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Already have an account?",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
                TextButton(
                    onClick = { navController.navigate(Screen.SignIn.route) },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Emerald
                    )
                ) {
                    Text(
                        "Sign In",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun SignUpTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    isVisible: Boolean = false,
    onVisibilityChange: () -> Unit = {},
    isError: Boolean = false,
    supportingText: String? = null,
    iconColor: Color = DeepBlue
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                label,
                color = if (isError) Rose else TextSecondary
            )
        },
        textStyle = LocalTextStyle.current.copy( // Add this to change text color
            color = DeepBlue,
            fontSize = 16.sp
        ),
        leadingIcon = {
            Icon(
                icon,
                contentDescription = null,
                tint = if (isError) Rose else iconColor,
                modifier = Modifier.scale(if (isError) 1.1f else 1f)
            )
        },
        trailingIcon = if (isPassword) {
            {
                IconButton(
                    onClick = onVisibilityChange,
                    modifier = Modifier.animateContentSize()
                ) {
                    Icon(
                        if (isVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = null,
                        tint = if (isError) Rose else SlateBlue
                    )
                }
            }
        } else null,
        visualTransformation = if (isPassword && !isVisible) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        isError = isError,
        supportingText = supportingText?.let {
            {
                Text(
                    it,
                    color = Rose,
                    fontSize = 12.sp
                )
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = if (isError) Rose else DeepBlue,
            unfocusedBorderColor = if (isError) Rose.copy(alpha = 0.5f) else BorderLight,
            focusedLabelColor = if (isError) Rose else DeepBlue,
            cursorColor = if (isError) Rose else DeepBlue,
            focusedLeadingIconColor = if (isError) Rose else DeepBlue,
            unfocusedLeadingIconColor = if (isError) Rose else TextSecondary,
            focusedTrailingIconColor = if (isError) Rose else DeepBlue,
            unfocusedTrailingIconColor = if (isError) Rose else TextSecondary,
            focusedTextColor = DeepBlue, // Add this for focused state text color
            unfocusedTextColor = DeepBlue // Add this for unfocused state text color
        )
    )
}
@Composable
fun PasswordRequirement(
    text: String,
    isValid: Boolean,
    color: Color = DeepBlue
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = if (isValid) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
            contentDescription = null,
            tint = if (isValid) Emerald else TextMuted,
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = if (isValid) TextPrimary else TextMuted,
            fontSize = 11.sp
        )
    }
}
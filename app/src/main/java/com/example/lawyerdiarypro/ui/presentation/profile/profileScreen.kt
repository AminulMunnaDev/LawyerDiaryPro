package com.example.lawyerdiarypro.ui.presentation.profile

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lawyerdiarypro.ui.presentation.home.HomeViewModel
import androidx.navigation.NavHostController
import androidx.compose.runtime.collectAsState
import com.example.lawyerdiarypro.ui.theme.Amber
import com.example.lawyerdiarypro.ui.theme.BorderLight
import com.example.lawyerdiarypro.ui.theme.DeepBlue
import com.example.lawyerdiarypro.ui.theme.Emerald
import com.example.lawyerdiarypro.ui.theme.Rose
import com.example.lawyerdiarypro.ui.theme.SlateBlue
import com.example.lawyerdiarypro.ui.theme.SurfaceLight
import com.example.lawyerdiarypro.ui.theme.TextMuted
import com.example.lawyerdiarypro.ui.theme.TextPrimary
import com.example.lawyerdiarypro.ui.theme.TextSecondary
import com.example.lawyerdiarypro.ui.theme.White
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.*
@Composable
fun ProfileScreen(
    navController: NavHostController,
    profileViewModel: ProfileViewModel = viewModel(),
    homeViewModel: HomeViewModel = viewModel()
) {
    val userProfile by profileViewModel.userProfile.collectAsState()
    val allCases by homeViewModel.allCases.collectAsState()
    val importantCases by homeViewModel.importantCases.collectAsState()

    // Animation states
    val screenAnimation = remember { Animatable(0f) }

    LaunchedEffect(key1 = true) {
        screenAnimation.animateTo(
            targetValue = 1f,
            animationSpec = tween(800, easing = FastOutSlowInEasing)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceLight)
            .alpha(screenAnimation.value)
            .verticalScroll(rememberScrollState())
    ) {
        // Profile Header with Cover
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(DeepBlue, SlateBlue)
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                // Profile Avatar
                Surface(
                    modifier = Modifier
                        .size(100.dp)
                        .offset(y = 50.dp)
                        .shadow(elevation = 8.dp, shape = CircleShape),
                    shape = CircleShape,
                    color = White
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        if (userProfile?.profileImage != null) {
                            // Show profile image
                        } else {
                            Text(
                                text = userProfile?.name?.take(2)?.uppercase() ?: "JD",
                                style = MaterialTheme.typography.headlineLarge,
                                color = DeepBlue,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }

                        // Edit button overlay
                        IconButton(
                            onClick = { /* Edit profile picture */ },
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .size(32.dp)
                                .background(
                                    color = Emerald,
                                    shape = CircleShape
                                )
                        ) {
                            Icon(
                                Icons.Default.Edit,
                                contentDescription = "Edit",
                                tint = White,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        }

        // Profile Info
        Surface(
            color = White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = userProfile?.name ?: "Adv. John Doe",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = DeepBlue
                )

                Text(
                    text = userProfile?.email ?: "john.doe@lawfirm.com",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )

                Text(
                    text = userProfile?.barCouncilId ?: "BCI/1234/2015",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextMuted,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Practice Areas
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    userProfile?.practiceAreas?.forEach { area ->
                        Surface(
                            color = DeepBlue.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text(
                                text = area,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                style = MaterialTheme.typography.labelSmall,
                                color = DeepBlue
                            )
                        }
                    }
                }
            }
        }

        // Statistics Cards
        Surface(
            color = White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = "Case Summary",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = DeepBlue,
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Stats Grid
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatCard(
                        title = "Total Cases",
                        value = allCases.size.toString(),
                        icon = Icons.Default.Folder,
                        color = DeepBlue,
                        modifier = Modifier.weight(1f)
                    )


                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatCard(
                        title = "Priority",
                        value = importantCases.size.toString(),
                        icon = Icons.Default.Star,
                        color = Amber,
                        modifier = Modifier.weight(1f)
                    )

                }

                Spacer(modifier = Modifier.height(16.dp))

                // Upcoming Hearings
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceLight),
                    border = BorderStroke(1.dp, BorderLight)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Upcoming Hearings",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = DeepBlue
                            )

                            Text(
                                text = "Next 7 days",
                                style = MaterialTheme.typography.labelSmall,
                                color = TextMuted
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        val upcomingCount = allCases.count {
                            it.hearingDate > System.currentTimeMillis() &&
                                    it.hearingDate < System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000
                        }

                        Text(
                            text = "$upcomingCount hearings scheduled",
                            style = MaterialTheme.typography.bodyLarge,
                            color = TextPrimary
                        )

                        if (upcomingCount > 0) {
                            Spacer(modifier = Modifier.height(8.dp))

                            allCases
                                .filter {
                                    it.hearingDate > System.currentTimeMillis() &&
                                            it.hearingDate < System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000
                                }
                                .take(3)
                                .forEach { case ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(6.dp)
                                                .background(
                                                    if (case.isImportant) Amber else Emerald,
                                                    CircleShape
                                                )
                                        )

                                        Spacer(modifier = Modifier.width(8.dp))

                                        Text(
                                            text = case.title,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = TextPrimary,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            modifier = Modifier.weight(1f)
                                        )

                                        Text(
                                            text = SimpleDateFormat("dd MMM", Locale.getDefault())
                                                .format(Date(case.hearingDate)),
                                            style = MaterialTheme.typography.bodySmall,
                                            color = TextSecondary
                                        )
                                    }
                                }
                        }
                    }
                }
            }
        }

        // Settings Sections
        SettingsSection(
            title = "Account Settings",
            items = listOf(
                SettingItem("Edit Profile", Icons.Default.Edit, DeepBlue),
                SettingItem("Change Password", Icons.Default.Lock, DeepBlue),
                SettingItem("Notification Settings", Icons.Default.Notifications, DeepBlue),
                SettingItem("Privacy & Security", Icons.Default.Security, DeepBlue)
            )
        )

        SettingsSection(
            title = "Preferences",
            items = listOf(
                SettingItem("Theme", Icons.Default.Palette, SlateBlue),
                SettingItem("Language", Icons.Default.Language, SlateBlue),
                SettingItem("Font Size", Icons.Default.TextFields, SlateBlue)
            )
        )

        SettingsSection(
            title = "Support",
            items = listOf(
                SettingItem("Help Center", Icons.Default.Help, Emerald),
                SettingItem("Terms of Service", Icons.Default.Description, Emerald),
                SettingItem("Privacy Policy", Icons.Default.Policy, Emerald),
                SettingItem("Contact Us", Icons.Default.Email, Emerald)
            )
        )

        // Logout Button
        Button(
            onClick = { /* Logout */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Rose,
                contentColor = White
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    Icons.Default.Logout,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Logout",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(2.dp),
        border = BorderStroke(1.dp, BorderLight)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = color,
                    fontSize = 24.sp
                )

                Surface(
                    color = color.copy(alpha = 0.1f),
                    shape = CircleShape,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        icon,
                        contentDescription = null,
                        tint = color,
                        modifier = Modifier.padding(6.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
        }
    }
}

@Composable
fun SettingsSection(
    title: String,
    items: List<SettingItem>
) {
    Surface(
        color = White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = DeepBlue
            )

            Spacer(modifier = Modifier.height(12.dp))

            items.forEach { item ->
                SettingItemRow(item)
            }
        }
    }
}

@Composable
fun SettingItemRow(item: SettingItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { item.onClick?.invoke() }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                color = item.color.copy(alpha = 0.1f),
                shape = CircleShape,
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    item.icon,
                    contentDescription = item.title,
                    tint = item.color,
                    modifier = Modifier.padding(8.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = item.title,
                style = MaterialTheme.typography.bodyLarge,
                color = TextPrimary
            )
        }

        Icon(
            Icons.AutoMirrored.Filled.ArrowForwardIos,
            contentDescription = null,
            tint = TextMuted,
            modifier = Modifier.size(16.dp)
        )
    }
}

data class SettingItem(
    val title: String,
    val icon: ImageVector,
    val color: Color,
    val onClick: (() -> Unit)? = null
)

// User Profile Data Class
data class UserProfile(
    val name: String,
    val email: String,
    val barCouncilId: String,
    val practiceAreas: List<String>,
    val profileImage: String? = null,
    val phone: String,
    val officeAddress: String
)

// Profile ViewModel
class ProfileViewModel : ViewModel() {
    private val _userProfile = MutableStateFlow<UserProfile?>(null)
    val userProfile: StateFlow<UserProfile?> = _userProfile.asStateFlow()

    init {
        loadUserProfile()
    }

    private fun loadUserProfile() {
        // Load from SharedPreferences or database
        _userProfile.value = UserProfile(
            name = "Adv. John Doe",
            email = "john.doe@lawfirm.com",
            barCouncilId = "BCI/1234/2015",
            practiceAreas = listOf("Criminal Law", "Corporate Law", "Civil Litigation"),
            phone = "+91 98765 43210",
            officeAddress = "123, Legal Chambers, Court Road, New Delhi"
        )
    }
}

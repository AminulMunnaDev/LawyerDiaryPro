package com.example.lawyerdiarypro.ui.presentation.home


import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.compose.runtime.collectAsState
import com.example.lawyerdiarypro.ui.Screen
import com.example.lawyerdiarypro.ui.database.CaseEntity
import com.example.lawyerdiarypro.ui.presentation.pdf.PdfBooksScreen
import com.example.lawyerdiarypro.ui.presentation.profile.ProfileScreen
import com.example.lawyerdiarypro.ui.presentation.schedule.ScheduleScreen
import com.example.lawyerdiarypro.ui.theme.Amber
import com.example.lawyerdiarypro.ui.theme.BorderLight
import com.example.lawyerdiarypro.ui.theme.DeepBlue
import com.example.lawyerdiarypro.ui.theme.Emerald
import com.example.lawyerdiarypro.ui.theme.SlateBlue
import com.example.lawyerdiarypro.ui.theme.SurfaceLight
import com.example.lawyerdiarypro.ui.theme.TextMuted
import com.example.lawyerdiarypro.ui.theme.TextPrimary
import com.example.lawyerdiarypro.ui.theme.TextSecondary
import com.example.lawyerdiarypro.ui.theme.White
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = viewModel()
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }

    // Animation states
    val headerAnimation = remember { Animatable(0f) }
    val contentAnimation = remember { Animatable(0f) }

    // Retrieve data from Room via ViewModel
    val allCases by homeViewModel.allCases.collectAsState()
    val importantCases by homeViewModel.importantCases.collectAsState()

    LaunchedEffect(key1 = true) {
        headerAnimation.animateTo(
            targetValue = 1f,
            animationSpec = tween(600, easing = FastOutSlowInEasing)
        )
        delay(200)
        contentAnimation.animateTo(
            targetValue = 1f,
            animationSpec = tween(800, easing = FastOutSlowInEasing)
        )
    }

    Scaffold(
        bottomBar = {
            LawyerBottomBar(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )
        },
        containerColor = SurfaceLight
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // --- PERMANENT HEADER with animation ---
            Surface(
                color = White,
                shadowElevation = 4.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(headerAnimation.value)
                    .scale(headerAnimation.value)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Hello, Counselor",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.ExtraBold,
                            color = DeepBlue,
                            fontSize = 28.sp
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.CalendarToday,
                                contentDescription = null,
                                tint = Emerald,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = SimpleDateFormat("EEEE, MMM d", Locale.getDefault()).format(
                                    Date()
                                ),
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextSecondary
                            )
                        }
                    }

                    // Profile avatar with gradient
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(DeepBlue, SlateBlue)
                                ),
                                CircleShape
                            )
                            .shadow(elevation = 4.dp, shape = CircleShape)
                    ) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(14.dp)
                                .fillMaxSize(),
                            tint = White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // --- SCROLLABLE CONTENT AREA ---
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(contentAnimation.value)
            ) {
                when (selectedTab) {
                    0 -> {
                        // Tab 0: Home Dashboard
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 24.dp)
                                .padding(bottom = 80.dp), // Move bottom padding to modifier
                            verticalArrangement = Arrangement.spacedBy(20.dp),
                            contentPadding = PaddingValues(vertical = 16.dp) // Keep only vertical padding here
                        ) {
                            // 1. Search Bar
                            item {
                                Surface(
                                    color = White,
                                    shape = RoundedCornerShape(16.dp),
                                    shadowElevation = 2.dp,
                                    border = BorderStroke(1.dp, BorderLight),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .animateContentSize()
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(horizontal = 16.dp)
                                    ) {
                                        Icon(
                                            Icons.Default.Search,
                                            contentDescription = null,
                                            tint = TextMuted,
                                            modifier = Modifier.size(20.dp)
                                        )

                                        OutlinedTextField(
                                            value = searchQuery,
                                            onValueChange = { searchQuery = it },
                                            modifier = Modifier
                                                .weight(1f)
                                                .padding(vertical = 4.dp),
                                            placeholder = {
                                                Text(
                                                    "Search cases or clients...",
                                                    color = TextMuted,
                                                    style = MaterialTheme.typography.bodyMedium
                                                )
                                            },
                                            colors = OutlinedTextFieldDefaults.colors(
                                                focusedBorderColor = Color.Transparent,
                                                unfocusedBorderColor = Color.Transparent,
                                                focusedContainerColor = Color.Transparent,
                                                unfocusedContainerColor = Color.Transparent
                                            ),
                                            singleLine = true
                                        )

                                        if (searchQuery.isNotEmpty()) {
                                            IconButton(
                                                onClick = { searchQuery = "" },
                                                modifier = Modifier.size(24.dp)
                                            ) {
                                                Icon(
                                                    Icons.Default.Close,
                                                    contentDescription = "Clear",
                                                    tint = TextMuted,
                                                    modifier = Modifier.size(16.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            // 2. Create Case Button
                            item {
                                Button(
                                    onClick = { navController.navigate(Screen.CreateCase.route) },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(80.dp),
                                    shape = RoundedCornerShape(16.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = DeepBlue,
                                        contentColor = White
                                    ),
                                    elevation = ButtonDefaults.buttonElevation(
                                        defaultElevation = 2.dp,
                                        pressedElevation = 4.dp
                                    )
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Surface(
                                                color = White.copy(alpha = 0.2f),
                                                shape = CircleShape,
                                                modifier = Modifier.size(40.dp)
                                            ) {
                                                Icon(
                                                    Icons.Default.Add,
                                                    contentDescription = null,
                                                    tint = White,
                                                    modifier = Modifier.padding(8.dp)
                                                )
                                            }
                                            Spacer(modifier = Modifier.width(16.dp))
                                            Column(
                                                horizontalAlignment = Alignment.Start
                                            ) {
                                                Text(
                                                    "Create a new case",
                                                    style = MaterialTheme.typography.titleLarge,
                                                    fontWeight = FontWeight.SemiBold,
                                                    color = White,
                                                    fontSize = 18.sp
                                                )
                                                Text(
                                                    "Add case details and documents",
                                                    style = MaterialTheme.typography.labelSmall,
                                                    color = White.copy(alpha = 0.8f)
                                                )
                                            }
                                        }
                                        Icon(
                                            Icons.AutoMirrored.Filled.ArrowForward,
                                            contentDescription = null,
                                            tint = White
                                        )
                                    }
                                }
                            }

                            // 3. Home Dashboard Lists
                            item {
                                HomeDashboard(
                                    allCases = allCases,
                                    importantCases = importantCases,
                                    navController = navController
                                )
                            }
                        }
                    }

                    1 -> ScheduleScreen(navController)
                    2 -> PdfBooksScreen(navController)
                    3 -> ProfileScreen(navController)
                }
            }
        }
    }
}



@Composable
fun HomeDashboard(
    allCases: List<CaseEntity>,
    importantCases: List<CaseEntity>,
    navController: NavHostController
) {
    Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {

        // --- IMPORTANT CASES SECTION ---
        if (importantCases.isNotEmpty()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        tint = Amber,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Priority Hearings",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = DeepBlue,
                        fontSize = 20.sp
                    )
                }
                Text(
                    text = "${importantCases.size} cases",
                    color = TextSecondary,
                    style = MaterialTheme.typography.labelMedium
                )
            }

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(importantCases) { case ->
                    ImportantCaseCard(
                        case = case,
                        navController = navController,
                        index = importantCases.indexOf(case)
                    )
                }
            }
        }

        // --- RECENT CASES SECTION ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Folder,
                    contentDescription = null,
                    tint = DeepBlue,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Recent Case Files",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = DeepBlue,
                    fontSize = 20.sp
                )
            }
            Text(
                text = "${allCases.size} total",
                color = TextSecondary,
                style = MaterialTheme.typography.labelMedium
            )
        }

        if (allCases.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Default.FolderOpen,
                        contentDescription = null,
                        tint = TextMuted,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No cases recorded yet",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextSecondary
                    )
                    Text(
                        text = "Tap 'Create a new case' to get started",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextMuted
                    )
                }
            }
        } else {
            allCases.take(5).forEachIndexed { index, case ->
                CaseListItem(
                    case = case,
                    navController = navController,
                    index = index
                )
            }

            if (allCases.size > 5) {
                TextButton(
                    onClick = { /* Navigate to all cases */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "View all ${allCases.size} cases",
                        color = DeepBlue,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
fun ImportantCaseCard(
    case: CaseEntity,
    navController: NavHostController,
    index: Int
) {
    val animatedScale = remember { Animatable(0.9f) }
    val animatedAlpha = remember { Animatable(0f) }

    LaunchedEffect(key1 = true) {
        delay(index * 100L)
        animatedAlpha.animateTo(1f, tween(500))
        animatedScale.animateTo(1f, spring(Spring.DampingRatioMediumBouncy))
    }

    Card(
        modifier = Modifier
            .width(220.dp)
            .scale(animatedScale.value)
            .alpha(animatedAlpha.value)
            .clickable { navController.navigate(Screen.CaseDetails.passId(case.id)) },
        colors = CardDefaults.cardColors(
            containerColor = White
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
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
                Surface(
                    color = DeepBlue.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = case.caseType,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = DeepBlue,
                        fontWeight = FontWeight.Bold
                    )
                }
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(Amber, CircleShape)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = case.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.ExtraBold,
                color = TextPrimary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Event,
                    contentDescription = null,
                    tint = Emerald,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Next: ${
                        SimpleDateFormat(
                            "dd MMM yyyy",
                            Locale.getDefault()
                        ).format(case.hearingDate)
                    }",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = null,
                    tint = SlateBlue,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = case.clientName,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun CaseListItem(
    case: CaseEntity,
    navController: NavHostController,
    index: Int
) {
    val animatedOffset = remember { Animatable(50f) }
    val animatedAlpha = remember { Animatable(0f) }

    LaunchedEffect(key1 = true) {
        delay(index * 100L)
        animatedAlpha.animateTo(1f, tween(500))
        animatedOffset.animateTo(0f, tween(500, easing = FastOutSlowInEasing))
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .offset(y = animatedOffset.value.dp)
            .alpha(animatedAlpha.value)
            .clickable { navController.navigate(Screen.CaseDetails.passId(case.id)) },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(2.dp),
        border = BorderStroke(1.dp, BorderLight)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Case icon/avatar
            Surface(
                color = DeepBlue.copy(alpha = 0.1f),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    Icons.Default.Gavel,
                    contentDescription = null,
                    tint = DeepBlue,
                    modifier = Modifier.padding(12.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = case.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextPrimary
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Text(
                        text = case.caseNumber,
                        style = MaterialTheme.typography.bodySmall,
                        color = DeepBlue,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = " • ",
                        color = TextMuted
                    )
                    Text(
                        text = case.clientName,
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                }
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = TextMuted
            )
        }
    }
}

@Composable
fun LawyerBottomBar(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    val items = listOf(
        Triple("Home", Icons.Default.Home, Icons.Outlined.Home),
        Triple("Schedule", Icons.Default.DateRange, Icons.Outlined.DateRange),
        Triple("Library", Icons.Default.MenuBook, Icons.Outlined.MenuBook),
        Triple("Profile", Icons.Default.Person, Icons.Outlined.Person)
    )

    Surface(
        color = White,
        tonalElevation = 8.dp,
        shadowElevation = 16.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .navigationBarsPadding()
                .padding(vertical = 12.dp, horizontal = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            items.forEachIndexed { index, item ->
                val isSelected = selectedTab == index

                // We animate the weight so the expansion is smooth
                val weight by animateFloatAsState(
                    targetValue = if (isSelected) 1.5f else 0.8f,
                    animationSpec = spring(Spring.DampingRatioLowBouncy, Spring.StiffnessLow),
                    label = "weight"
                )

                Box(
                    modifier = Modifier
                        .weight(weight)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null // Removes the grey ripple for a cleaner look
                        ) { onTabSelected(index) }, contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier
                            .animateContentSize(
                                spring(
                                    Spring.DampingRatioLowBouncy, Spring.StiffnessLow
                                )
                            )
                            .background(
                                color = if (isSelected) DeepBlue.copy(alpha = 0.1f) else Color.Transparent,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = if (isSelected) item.second else item.third,
                            contentDescription = item.first,
                            tint = if (isSelected) DeepBlue else TextMuted,
                            modifier = Modifier.size(24.dp)
                        )

                        if (isSelected) {
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = item.first,
                                color = DeepBlue,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                maxLines = 1,
                                softWrap = false
                            )
                        }
                    }
                }
            }
        }
    }
}

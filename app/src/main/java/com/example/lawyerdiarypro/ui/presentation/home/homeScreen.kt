import android.icu.text.SimpleDateFormat
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.lawyerdiarypro.ui.Screen
import com.example.lawyerdiarypro.ui.database.CaseEntity
import com.example.lawyerdiarypro.ui.presentation.home.HomeViewModel
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
import java.util.*

@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = viewModel()
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }

    val allCases by homeViewModel.allCases.collectAsState()
    val importantCases by homeViewModel.importantCases.collectAsState()

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

            // HEADER (no animation)
            Surface(
                color = White,
                shadowElevation = 4.dp,
                modifier = Modifier.fillMaxWidth()
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

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.CalendarToday,
                                contentDescription = null,
                                tint = Emerald,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = SimpleDateFormat(
                                    "EEEE, MMM d",
                                    Locale.getDefault()
                                ).format(Date()),
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextSecondary
                            )
                        }
                    }

                    // Profile
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(DeepBlue, SlateBlue)
                                ),
                                CircleShape
                            )
                            .shadow(4.dp, CircleShape)
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

            // CONTENT
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                when (selectedTab) {
                    0 -> LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 24.dp)
                            .padding(bottom = 80.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        contentPadding = PaddingValues(vertical = 16.dp)
                    ) {

                        // Search
                        item {
                            AnimatedSection {
                                SearchBar(searchQuery) { searchQuery = it }
                            }
                        }

                        // Create Case Button
                        item {
                            AnimatedSection {
                                CreateCaseButton(navController)
                            }
                        }

                        // Dashboard
                        item {
                            AnimatedSection {
                                HomeDashboard(allCases, importantCases, navController)
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

// --------------------
// Reusable Animated Wrapper
// --------------------
@Composable
fun AnimatedSection(
    visible: Boolean = true,
    fade: Boolean = true,
    scale: Boolean = true,
    content: @Composable () -> Unit
) {
    val alpha by animateFloatAsState(if (visible && fade) 1f else 0f)
    val scaleVal by animateFloatAsState(if (visible && scale) 1f else 0.95f)

    Box(
        modifier = Modifier
            .graphicsLayer {
                this.alpha = alpha
                this.scaleX = scaleVal
                this.scaleY = scaleVal
            }
    ) {
        content()
    }
}

// --------------------
// Small components for clarity
// --------------------
@Composable
fun SearchBar(query: String, onQueryChange: (String) -> Unit) {
    Surface(
        color = White,
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 2.dp,
        border = BorderStroke(1.dp, BorderLight),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Icon(
                Icons.Default.Search,
                contentDescription = null,
                tint = TextMuted
            )

            OutlinedTextField(
                value = query,
                onValueChange = onQueryChange,
                modifier = Modifier.weight(1f),
                placeholder = { Text("Search cases or clients...") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                ),
                singleLine = true
            )

            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(Icons.Default.Close, null)
                }
            }
        }
    }
}

@Composable
fun CreateCaseButton(navController: NavHostController) {
    Button(
        onClick = { navController.navigate(Screen.CreateCase.route) },
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = DeepBlue,
            contentColor = White
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {
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

                Column {
                    Text("Create a new case")
                    Text("Add case details and documents")
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

// ------------------------- HomeDashboard.kt -------------------------

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
                Row(verticalAlignment = Alignment.CenterVertically) {
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
                    Card(
                        modifier = Modifier
                            .width(220.dp)
                            .clickable { navController.navigate(Screen.CaseDetails.passId(case.id)) },
                        colors = CardDefaults.cardColors(containerColor = White),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(4.dp),
                        border = BorderStroke(1.dp, BorderLight)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
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

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Default.Event,
                                    contentDescription = null,
                                    tint = Emerald,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Next: ${SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(case.hearingDate)}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextSecondary
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(verticalAlignment = Alignment.CenterVertically) {
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
            }
        }

        // --- RECENT CASES SECTION ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
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
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
            allCases.take(5).forEach { case ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navController.navigate(Screen.CaseDetails.passId(case.id)) },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = White),
                    elevation = CardDefaults.cardElevation(2.dp),
                    border = BorderStroke(1.dp, BorderLight)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
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

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = case.title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.ExtraBold,
                                color = TextPrimary
                            )
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 4.dp)) {
                                Text(text = case.caseNumber, style = MaterialTheme.typography.bodySmall, color = DeepBlue)
                                Text(text = " • ", color = TextMuted)
                                Text(text = case.clientName, style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                            }
                        }

                      Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = TextMuted
                        )
                    }
                }
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

// ------------------------- LawyerBottomBar.kt -------------------------

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
                val weight by animateFloatAsState(targetValue = if (isSelected) 1.5f else 0.8f)

                Box(
                    modifier = Modifier
                        .weight(weight)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { onTabSelected(index) },
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier
                            .animateContentSize()
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
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
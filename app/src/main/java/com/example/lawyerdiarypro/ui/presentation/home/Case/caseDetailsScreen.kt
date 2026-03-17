package com.example.lawyerdiarypro.ui.presentation.home.Case

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.animateContentSize
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.compose.runtime.collectAsState
import com.example.lawyerdiarypro.ui.Screen
import com.example.lawyerdiarypro.ui.theme.Amber
import com.example.lawyerdiarypro.ui.theme.BorderLight
import com.example.lawyerdiarypro.ui.theme.DeepBlue
import com.example.lawyerdiarypro.ui.theme.Emerald
import com.example.lawyerdiarypro.ui.theme.MediumGray
import com.example.lawyerdiarypro.ui.theme.Rose
import com.example.lawyerdiarypro.ui.theme.SlateBlue
import com.example.lawyerdiarypro.ui.theme.SurfaceLight
import com.example.lawyerdiarypro.ui.theme.TextPrimary
import com.example.lawyerdiarypro.ui.theme.TextSecondary
import com.example.lawyerdiarypro.ui.theme.White
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaseDetailsScreen(
    caseId: Int,
    navController: NavHostController,
    viewModel: CaseDetailsViewModel = viewModel()
) {
    val selectedCase by viewModel.selectedCase.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // Animation states
    val screenAnimation = remember { Animatable(0f) }
    val contentAnimation = remember { Animatable(0f) }
    val sectionsAnimation = List(7) { remember { Animatable(0f) } } // For 7 sections

    LaunchedEffect(caseId) {
        viewModel.loadCase(caseId)

        // Start animations
        screenAnimation.animateTo(
            targetValue = 1f,
            animationSpec = tween(600, easing = FastOutSlowInEasing)
        )

        delay(200)
        contentAnimation.animateTo(
            targetValue = 1f,
            animationSpec = tween(800, easing = FastOutSlowInEasing)
        )

        // Animate sections sequentially
        sectionsAnimation.forEachIndexed { index, animatable ->
            delay(index * 150L)
            animatable.animateTo(
                targetValue = 1f,
                animationSpec = tween(500, easing = FastOutSlowInEasing)
            )
        }
    }

    Scaffold(
        containerColor = SurfaceLight,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Case Overview",
                        color = White,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 22.sp,
                        letterSpacing = 0.5.sp
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier
                            .scale(screenAnimation.value)
                            .rotate(screenAnimation.value * 360f)
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DeepBlue,
                    titleContentColor = White,
                    actionIconContentColor = White
                ),
                actions = {
                    IconButton(
                        onClick = { navController.navigate("create_case?caseId=$caseId") },
                        modifier = Modifier
                            .scale(screenAnimation.value)
                            .rotate(screenAnimation.value * 360f)
                    ) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = White
                        )
                    }
                    IconButton(
                        onClick = { showDeleteDialog = true },
                        modifier = Modifier
                            .scale(screenAnimation.value)
                            .rotate(screenAnimation.value * 360f)
                    ) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = White
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
                .background(SurfaceLight)
                .alpha(contentAnimation.value)
        ) {
            selectedCase?.let { case ->
                Spacer(modifier = Modifier.height(16.dp))

                // Priority Tag with enhanced animation
                if (case.isImportant) {
                    val pulseAnimation = remember { Animatable(0.8f) }

                    LaunchedEffect(key1 = true) {
                        pulseAnimation.animateTo(
                            targetValue = 1.2f,
                            animationSpec = infiniteRepeatable(
                                animation = tween(800, easing = FastOutSlowInEasing),
                                repeatMode = RepeatMode.Reverse
                            )
                        )
                    }

                    Surface(
                        color = Amber.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .scale(pulseAnimation.value)
                            .alpha(pulseAnimation.value)
                            .animateContentSize()
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Star,
                                contentDescription = null,
                                tint = Amber,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "PRIORITY CASE",
                                style = MaterialTheme.typography.labelLarge,
                                color = DeepBlue,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 14.sp,
                                letterSpacing = 1.sp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                Icons.Default.Star,
                                contentDescription = null,
                                tint = Amber,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }

                // --- Section: Core Information ---
                DetailHeader(
                    "Core Information",
                    Icons.Default.Info,
                    DeepBlue,
                    modifier = Modifier.alpha(sectionsAnimation[0].value)
                )

                DetailRow(
                    "Case Title",
                    case.title,
                    DeepBlue,
                    modifier = Modifier.alpha(sectionsAnimation[0].value)
                )
                DetailRow(
                    "Case Number",
                    case.caseNumber,
                    DeepBlue,
                    modifier = Modifier.alpha(sectionsAnimation[0].value)
                )
                DetailRow(
                    "Case Type",
                    case.caseType,
                    DeepBlue,
                    modifier = Modifier.alpha(sectionsAnimation[0].value)
                )
                DetailRow(
                    "Court Name",
                    case.courtName,
                    DeepBlue,
                    modifier = Modifier.alpha(sectionsAnimation[0].value)
                )
                DetailRow(
                    "Hearing Date",
                    case.hearingDate.let {
                        if (it == 0L) "Not Set"
                        else SimpleDateFormat(
                            "dd MMM yyyy",
                            Locale.getDefault()
                        ).format(it)
                    },
                    if (case.hearingDate != 0L) Emerald else MediumGray,
                    modifier = Modifier.alpha(sectionsAnimation[0].value)
                )

                // --- Section: Legal Identifiers ---
                DetailHeader(
                    "Legal Identifiers",
                    Icons.Default.Description,
                    SlateBlue,
                    modifier = Modifier.alpha(sectionsAnimation[1].value)
                )

                DetailRow(
                    "CNR Number",
                    case.cnrNumber.ifEmpty { "N/A" },
                    DeepBlue,
                    modifier = Modifier.alpha(sectionsAnimation[1].value)
                )
                DetailRow(
                    "Filing Number",
                    case.filingNumber.ifEmpty { "N/A" },
                    DeepBlue,
                    modifier = Modifier.alpha(sectionsAnimation[1].value)
                )
                DetailRow(
                    "Party (v/s)",
                    case.partyNameVs.ifEmpty { "N/A" },
                    DeepBlue,
                    modifier = Modifier.alpha(sectionsAnimation[1].value)
                )
                DetailRow(
                    "Appearing For",
                    case.appearingFor.ifEmpty { "N/A" },
                    DeepBlue,
                    modifier = Modifier.alpha(sectionsAnimation[1].value)
                )

                // --- Section: Court Officials ---
                DetailHeader(
                    "Court Officials",
                    Icons.Default.Gavel,
                    DeepBlue,
                    modifier = Modifier.alpha(sectionsAnimation[2].value)
                )

                DetailRow(
                    "Judge Name",
                    case.judgeName.ifEmpty { "N/A" },
                    DeepBlue,
                    modifier = Modifier.alpha(sectionsAnimation[2].value)
                )
                DetailRow(
                    "Opposing Counsel",
                    case.opposingAdvocate.ifEmpty { "N/A" },
                    DeepBlue,
                    modifier = Modifier.alpha(sectionsAnimation[2].value)
                )

                // --- Section: Criminal / FIR ---
                DetailHeader(
                    "FIR Details",
                    Icons.Default.LocalPolice,
                    SlateBlue,
                    modifier = Modifier.alpha(sectionsAnimation[3].value)
                )

                DetailRow(
                    "Police Station",
                    case.policeStation.ifEmpty { "N/A" },
                    DeepBlue,
                    modifier = Modifier.alpha(sectionsAnimation[3].value)
                )
                DetailRow(
                    "FIR No & Year",
                    if (case.firNumber.isEmpty()) "N/A" else "${case.firNumber} / ${case.firYear}",
                    DeepBlue,
                    modifier = Modifier.alpha(sectionsAnimation[3].value)
                )
                DetailRow(
                    "Acts & Sections",
                    case.actsAndSections.ifEmpty { "N/A" },
                    DeepBlue,
                    modifier = Modifier.alpha(sectionsAnimation[3].value)
                )

                // --- Section: Client Contact ---
                DetailHeader(
                    "Client Details",
                    Icons.Default.Person,
                    Emerald,
                    modifier = Modifier.alpha(sectionsAnimation[4].value)
                )

                DetailRow(
                    "Client Name",
                    case.clientName,
                    DeepBlue,
                    modifier = Modifier.alpha(sectionsAnimation[4].value)
                )

                // Phone with click to call
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .alpha(sectionsAnimation[4].value)
                        .clickable {
                            val intent = Intent(
                                Intent.ACTION_DIAL,
                                Uri.parse("tel:${case.clientPhone}")
                            )
                            context.startActivity(intent)
                        },
                    color = Color.Transparent
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Client Phone",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary,
                            modifier = Modifier.weight(1f)
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = case.clientPhone,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.SemiBold,
                                color = DeepBlue,
                                textAlign = TextAlign.End
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                Icons.Default.Phone,
                                contentDescription = "Call",
                                tint = Emerald,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
                HorizontalDivider(
                    thickness = 1.dp,
                    color = BorderLight,
                    modifier = Modifier.alpha(sectionsAnimation[4].value)
                )

                // --- Section: Notes ---
                DetailHeader(
                    "Private Notes",
                    Icons.Default.Note,
                    SlateBlue,
                    modifier = Modifier.alpha(sectionsAnimation[5].value)
                )

                Surface(
                    color = White,
                    shape = RoundedCornerShape(16.dp),
                    shadowElevation = 2.dp,
                    border = BorderStroke(1.dp, BorderLight),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp)
                        .scale(sectionsAnimation[5].value)
                        .alpha(sectionsAnimation[5].value)
                        .animateContentSize()
                ) {
                    Text(
                        text = case.notes.ifEmpty { "No additional notes provided." },
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextPrimary,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                // Case Actions
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                        .alpha(sectionsAnimation[6].value),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // Share button
                    ActionButton(
                        icon = Icons.Default.Share,
                        text = "Share",
                        color = DeepBlue,
                        onClick = {
                            val shareIntent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(
                                    Intent.EXTRA_TEXT,
                                    "Case: ${case.title}\nNumber: ${case.caseNumber}\nCourt: ${case.courtName}"
                                )
                                type = "text/plain"
                            }
                            context.startActivity(
                                Intent.createChooser(
                                    shareIntent,
                                    "Share Case Details"
                                )
                            )
                        }
                    )

                    // Calendar button
                    if (case.hearingDate != 0L) {
                        ActionButton(
                            icon = Icons.Default.Event,
                            text = "Add to Calendar",
                            color = Emerald,
                            onClick = {
                                // Add to calendar intent
                            }
                        )
                    }

                    // Print button
                    ActionButton(
                        icon = Icons.Default.Print,
                        text = "Print",
                        color = SlateBlue,
                        onClick = {
                            // Print functionality
                        }
                    )
                }
            }
        }
    }

    // --- DELETE CONFIRMATION DIALOG with animation ---
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            containerColor = White,
            titleContentColor = TextPrimary,
            textContentColor = TextSecondary,
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Warning,
                        contentDescription = null,
                        tint = Rose,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Delete Case?",
                        color = DeepBlue,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 20.sp
                    )
                }
            },
            text = {
                Text(
                    "Are you sure you want to permanently delete this case file? This action cannot be undone.",
                    color = TextSecondary
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        selectedCase?.let { case ->
                            viewModel.deleteCase(context, case) {
                                showDeleteDialog = false
                                navController.navigate(Screen.Home.route) {
                                    popUpTo(Screen.Home.route) { inclusive = true }
                                }
                            }
                        }
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Rose
                    )
                ) {
                    Text(
                        "Delete",
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDeleteDialog = false },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = SlateBlue
                    )
                ) {
                    Text(
                        "Cancel",
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        )
    }
}

@Composable
fun DetailHeader(
    title: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 24.dp, bottom = 8.dp)
    ) {
        Surface(
            color = color.copy(alpha = 0.1f),
            shape = CircleShape,
            modifier = Modifier.size(28.dp)
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.padding(6.dp)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title.uppercase(),
            style = MaterialTheme.typography.labelLarge,
            color = color,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 14.sp,
            letterSpacing = 0.8.sp
        )
    }
}

@Composable
fun DetailRow(
    label: String,
    value: String,
    valueColor: Color = DeepBlue,
    modifier: Modifier = Modifier
) {
    val animatedScale = remember { Animatable(0.95f) }

    LaunchedEffect(key1 = value) {
        animatedScale.animateTo(
            targetValue = 1f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .scale(animatedScale.value)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .animateContentSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = valueColor,
                modifier = Modifier.weight(1.5f),
                textAlign = TextAlign.End
            )
        }
        HorizontalDivider(
            thickness = 1.dp,
            color = BorderLight
        )
    }
}

@Composable
fun ActionButton(
    icon: ImageVector,
    text: String,
    color: Color,
    onClick: () -> Unit
) {
    val animatedScale = remember { Animatable(1f) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable {
                // Use LaunchedEffect with a key that changes on click
                // For now, just call onClick directly
                onClick()
            }
            .scale(animatedScale.value)
    ) {
        Surface(
            color = color.copy(alpha = 0.1f),
            shape = CircleShape,
            modifier = Modifier.size(48.dp)
        ) {
            Icon(
                icon,
                contentDescription = text,
                tint = color,
                modifier = Modifier.padding(12.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = TextSecondary,
            fontSize = 11.sp
        )
    }
}

package com.example.lawyerdiarypro.ui.presentation.schedule

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.EventBusy
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.lawyerdiarypro.ui.database.CaseEntity
import com.example.lawyerdiarypro.ui.theme.BorderLight
import com.example.lawyerdiarypro.ui.theme.DeepBlue
import com.example.lawyerdiarypro.ui.theme.Emerald
import com.example.lawyerdiarypro.ui.theme.Rose
import com.example.lawyerdiarypro.ui.theme.SurfaceLight
import com.example.lawyerdiarypro.ui.theme.TextMuted
import com.example.lawyerdiarypro.ui.theme.TextPrimary
import com.example.lawyerdiarypro.ui.theme.TextSecondary
import com.example.lawyerdiarypro.ui.theme.White
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScheduleScreen(
    navController: NavHostController,
    viewModel: ScheduleViewModel = viewModel()
) {
    val groupedCases by viewModel.groupedCases.collectAsState()
    val animatedProgress = remember { Animatable(0f) }

    LaunchedEffect(key1 = true) {
        animatedProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(800, easing = FastOutSlowInEasing)
        )
    }

    // Professional gradient background
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceLight)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            // --- FILTER SECTION with animation ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .alpha(animatedProgress.value)
                    .scale(animatedProgress.value),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        "Upcoming Schedule",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = DeepBlue,
                        fontSize = 28.sp
                    )

                    // Decorative line
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.3f)
                            .height(3.dp)
                            .padding(top = 4.dp)
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(Emerald, DeepBlue)
                                )
                            )
                    )
                }

                // Filter Icon with animation
                IconButton(
                    onClick = { /* Implement Month Picker Dialog */ },
                    modifier = Modifier
                        .background(
                            color = DeepBlue.copy(alpha = 0.1f),
                            shape = CircleShape
                        )
                        .size(48.dp)
                        .scale(animatedProgress.value)
                ) {
                    Icon(
                        Icons.Default.FilterList,
                        contentDescription = "Filter",
                        tint = DeepBlue
                    )
                }
            }

            if (groupedCases.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 80.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Default.EventBusy,
                            contentDescription = null,
                            tint = TextMuted,
                            modifier = Modifier.size(80.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            "No upcoming hearings scheduled.",
                            color = TextSecondary,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Pull down to refresh",
                            color = TextMuted,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 80.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    groupedCases.forEach { (date, cases) ->
                        // STICKY DATE HEADER with enhanced styling
                        stickyHeader {
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    ,
                                color = SurfaceLight,
                                shadowElevation = 2.dp
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .width(4.dp)
                                            .height(24.dp)
                                            .background(
                                                Brush.verticalGradient(
                                                    colors = listOf(DeepBlue, Emerald)
                                                ),
                                                shape = RoundedCornerShape(2.dp)
                                            )
                                    )

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Text(
                                        text = date,
                                        style = MaterialTheme.typography.titleMedium,
                                        color = DeepBlue,
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 16.sp
                                    )

                                    Spacer(modifier = Modifier.weight(1f))

                                    // Case count badge
                                    Surface(
                                        color = DeepBlue.copy(alpha = 0.1f),
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Text(
                                            text = "${cases.size} case${if (cases.size > 1) "s" else ""}",
                                            modifier = Modifier.padding(
                                                horizontal = 10.dp,
                                                vertical = 4.dp
                                            ),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = DeepBlue,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }

                        // ITEMS FOR THAT DATE with animations
                        items(cases, key = { it.id }) { case ->
                            ScheduleItem(
                                case = case,
                                onClick = {
                                    navController.navigate("details/${case.id}")
                                },
                                index = cases.indexOf(case)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ScheduleItem(
    case: CaseEntity,
    onClick: () -> Unit,
    index: Int
) {
    val animatedScale = remember { Animatable(0.9f) }
    val animatedAlpha = remember { Animatable(0f) }

    LaunchedEffect(key1 = true) {
        delay(index * 100L)
        animatedAlpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(500, easing = FastOutSlowInEasing)
        )
        animatedScale.animateTo(
            targetValue = 1f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(animatedScale.value)
            .alpha(animatedAlpha.value)
            .clickable { onClick() }
            ,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
            pressedElevation = 4.dp
        ),
        border = BorderStroke(1.dp, BorderLight)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Visual Time Marker with animation
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.animateContentSize()
            ) {
                // Priority indicator
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .background(
                            if (case.isImportant) {
                                Rose
                            } else {
                                Emerald
                            },
                            CircleShape
                        )
                        .scale(if (case.isImportant) 1.2f else 1f)
                )

                // Vertical timeline
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(40.dp)
                        .background(
                            Brush.verticalGradient(
                                colors = if (case.isImportant) {
                                    listOf(Rose.copy(alpha = 0.5f), Rose.copy(alpha = 0.2f))
                                } else {
                                    listOf(Emerald.copy(alpha = 0.5f), Emerald.copy(alpha = 0.2f))
                                }
                            )
                        )
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Case details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = case.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = TextPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 16.sp
                    )

                    if (case.isImportant) {
                        Surface(
                            color = Rose.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = "PRIORITY",
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall,
                                color = Rose,
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Gavel,
                        contentDescription = null,
                        tint = TextMuted,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = case.caseType,
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )

                    Text(
                        text = " • ",
                        color = TextMuted
                    )

                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = TextMuted,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = case.courtName,
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            // Case Number Tag with enhanced styling
            Surface(
                color = DeepBlue.copy(alpha = 0.1f),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.animateContentSize()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                ) {
                    Icon(
                        Icons.Default.Description,
                        contentDescription = null,
                        tint = DeepBlue,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = case.caseNumber,
                        style = MaterialTheme.typography.labelSmall,
                        color = DeepBlue,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}
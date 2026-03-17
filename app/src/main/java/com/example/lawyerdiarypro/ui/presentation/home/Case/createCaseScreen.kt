package com.example.lawyerdiarypro.ui.presentation.home.Case


import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Balance
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalPolice
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Note
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.lawyerdiarypro.ui.Screen
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
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCaseScreen(
    navController: NavHostController,
    caseId: Int? = null,
    viewModel: CreateCaseViewModel = viewModel()
) {
    val state = viewModel.state
    val datePickerState = rememberDatePickerState()
    var showDatePicker by remember { mutableStateOf(false) }

    // Animation states
    val screenAnimation = remember { Animatable(0f) }
    val fieldsAnimation = remember { Animatable(0f) }

    LaunchedEffect(caseId) {
        if (caseId != null && caseId != -1) {
            viewModel.loadCaseForEditing(caseId)
        }

        // Start animations
        screenAnimation.animateTo(
            targetValue = 1f,
            animationSpec = tween(600, easing = FastOutSlowInEasing)
        )
        delay(200)
        fieldsAnimation.animateTo(
            targetValue = 1f,
            animationSpec = tween(800, easing = FastOutSlowInEasing)
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (caseId != null) "Edit Case File" else "Create Case File",
                        fontWeight = FontWeight.ExtraBold,
                        color = White
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.scale(screenAnimation.value)
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DeepBlue,
                    titleContentColor = White,
                    navigationIconContentColor = White,
                    actionIconContentColor = White
                )
            )
        },
        containerColor = SurfaceLight
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
                .alpha(screenAnimation.value),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // --- MANDATORY SECTION ---
            SectionHeader(
                title = "Mandatory Information",
                icon = Icons.Default.Info,
                color = DeepBlue
            )

            CaseInputField(
                value = state.title,
                onValueChange = { viewModel.onEvent(CreateCaseEvent.TitleChanged(it)) },
                label = "Case Title*",
                isError = state.isTitleError,
                icon = Icons.Default.Description,
                iconColor = DeepBlue,
                modifier = Modifier.alpha(fieldsAnimation.value)
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.alpha(fieldsAnimation.value)
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    CaseInputField(
                        value = state.caseNumber,
                        onValueChange = { viewModel.onEvent(CreateCaseEvent.CaseNumberChanged(it)) },
                        label = "Case No.*",
                        icon = Icons.Default.Numbers,
                        iconColor = SlateBlue
                    )
                }
                Box(modifier = Modifier.weight(1f)) {
                    CaseTypeDropdown(
                        selectedType = state.caseType,
                        options = viewModel.caseTypes,
                        onSelect = { viewModel.onEvent(CreateCaseEvent.TypeChanged(it)) },
                        modifier = Modifier.alpha(fieldsAnimation.value)
                    )
                }
            }

            CaseInputField(
                value = state.courtName,
                onValueChange = { viewModel.onEvent(CreateCaseEvent.CourtNameChanged(it)) },
                label = "Court Name*",
                icon = Icons.Default.Gavel,
                iconColor = DeepBlue,
                modifier = Modifier.alpha(fieldsAnimation.value)
            )

            // Date Picker Card with animation
            OutlinedCard(
                onClick = { showDatePicker = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(fieldsAnimation.value)
                    .animateContentSize(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.outlinedCardColors(
                    containerColor = White
                ),
                border = BorderStroke(1.dp, if (state.hearingDate == null) BorderLight else Emerald)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = if (state.hearingDate == null) DeepBlue.copy(alpha = 0.1f) else Emerald.copy(
                            alpha = 0.1f
                        ),
                        shape = CircleShape,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            Icons.Default.CalendarToday,
                            contentDescription = null,
                            tint = if (state.hearingDate == null) DeepBlue else Emerald,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Next Hearing Date",
                            style = MaterialTheme.typography.labelMedium,
                            color = TextSecondary
                        )
                        Text(
                            text = state.hearingDate?.let {
                                SimpleDateFormat(
                                    "dd MMM yyyy",
                                    Locale.getDefault()
                                ).format(it)
                            } ?: "Select hearing date*",
                            color = if (state.hearingDate == null) TextMuted else TextPrimary,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = if (state.hearingDate != null) FontWeight.SemiBold else FontWeight.Normal
                        )
                    }
                }
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                color = BorderLight,
                thickness = 1.dp
            )

            // --- OPTIONAL LEGAL DETAILS ---
            SectionHeader(
                title = "Legal Details",
                icon = Icons.Default.Description,
                color = SlateBlue
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.alpha(fieldsAnimation.value)
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    CaseInputField(
                        value = state.filingNumber,
                        onValueChange = { viewModel.onEvent(CreateCaseEvent.FilingNumberChanged(it)) },
                        label = "Filing No.",
                        icon = Icons.Default.Note,
                        iconColor = SlateBlue
                    )
                }
                Box(modifier = Modifier.weight(1f)) {
                    CaseInputField(
                        value = state.cnrNumber,
                        onValueChange = { viewModel.onEvent(CreateCaseEvent.CnrNumberChanged(it)) },
                        label = "CNR Number",
                        icon = Icons.Default.Numbers,
                        iconColor = SlateBlue
                    )
                }
            }

            CaseInputField(
                value = state.partyNameVs,
                onValueChange = { viewModel.onEvent(CreateCaseEvent.PartyVsChanged(it)) },
                label = "Party Name (v/s)",
                icon = Icons.Default.People,
                iconColor = SlateBlue,
                modifier = Modifier.alpha(fieldsAnimation.value)
            )

            CaseInputField(
                value = state.appearingFor,
                onValueChange = { viewModel.onEvent(CreateCaseEvent.AppearingForChanged(it)) },
                label = "Appearing For",
                icon = Icons.Default.Badge,
                iconColor = SlateBlue,
                modifier = Modifier.alpha(fieldsAnimation.value)
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.alpha(fieldsAnimation.value)
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    CaseInputField(
                        value = state.judgeName,
                        onValueChange = { viewModel.onEvent(CreateCaseEvent.JudgeNameChanged(it)) },
                        label = "Judge Name",
                        icon = Icons.Default.Gavel,
                        iconColor = SlateBlue
                    )
                }
                Box(modifier = Modifier.weight(1f)) {
                    CaseInputField(
                        value = state.opposingAdvocate,
                        onValueChange = {
                            viewModel.onEvent(
                                CreateCaseEvent.OpposingAdvocateChanged(
                                    it
                                )
                            )
                        },
                        label = "Opp. Counsel",
                        icon = Icons.Default.Balance,
                        iconColor = SlateBlue
                    )
                }
            }

            // --- FIR SECTION ---
            SectionHeader(
                title = "FIR / Criminal Details",
                icon = Icons.Default.LocalPolice,
                color = DeepBlue
            )

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(fieldsAnimation.value),
                shape = RoundedCornerShape(16.dp),
                color = White,
                border = BorderStroke(1.dp, BorderLight),
                tonalElevation = 1.dp
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CaseInputField(
                        value = state.policeStation,
                        onValueChange = { viewModel.onEvent(CreateCaseEvent.PoliceStationChanged(it)) },
                        label = "Police Station",
                        icon = Icons.Default.LocationOn,
                        iconColor = DeepBlue
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Box(modifier = Modifier.weight(1.5f)) {
                            CaseInputField(
                                value = state.firNumber,
                                onValueChange = {
                                    viewModel.onEvent(
                                        CreateCaseEvent.FirNumberChanged(
                                            it
                                        )
                                    )
                                },
                                label = "FIR No.",
                                icon = Icons.Default.Numbers,
                                iconColor = DeepBlue
                            )
                        }
                        Box(modifier = Modifier.weight(1f)) {
                            CaseInputField(
                                value = state.firYear,
                                onValueChange = {
                                    viewModel.onEvent(
                                        CreateCaseEvent.FirYearChanged(
                                            it
                                        )
                                    )
                                },
                                label = "Year",
                                icon = Icons.Default.DateRange,
                                iconColor = DeepBlue
                            )
                        }
                    }
                }
            }

            CaseInputField(
                value = state.actsAndSections,
                onValueChange = { viewModel.onEvent(CreateCaseEvent.ActsSectionsChanged(it)) },
                label = "Acts & Sections",
                icon = Icons.AutoMirrored.Filled.MenuBook,
                iconColor = DeepBlue,
                modifier = Modifier.alpha(fieldsAnimation.value)
            )

            // --- CLIENT INFO ---
            SectionHeader(
                title = "Client Information",
                icon = Icons.Default.Person,
                color = Emerald
            )

            CaseInputField(
                value = state.clientName,
                onValueChange = { viewModel.onEvent(CreateCaseEvent.ClientNameChanged(it)) },
                label = "Client Name*",
                icon = Icons.Default.Person,
                iconColor = Emerald,
                modifier = Modifier.alpha(fieldsAnimation.value)
            )

            CaseInputField(
                value = state.clientPhone,
                onValueChange = { viewModel.onEvent(CreateCaseEvent.ClientPhoneChanged(it)) },
                label = "Phone*",
                icon = Icons.Default.Phone,
                iconColor = Emerald,
                isError = state.isPhoneError,
                supportingText = if (state.isPhoneError) "Please enter a valid phone number" else null,
                keyboardType = KeyboardType.Phone,
                modifier = Modifier.alpha(fieldsAnimation.value)
            )

            // Importance Switch with animation
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(fieldsAnimation.value),
                shape = RoundedCornerShape(12.dp),
                color = White,
                border = BorderStroke(1.dp, BorderLight)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Star,
                            contentDescription = null,
                            tint = if (state.isImportant) Amber else TextMuted,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            "Mark as Priority Case",
                            fontWeight = FontWeight.SemiBold,
                            color = if (state.isImportant) DeepBlue else TextSecondary
                        )
                    }
                    Switch(
                        checked = state.isImportant,
                        onCheckedChange = { viewModel.onEvent(CreateCaseEvent.ImportanceChanged(it)) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Amber,
                            checkedTrackColor = Amber.copy(alpha = 0.5f),
                            uncheckedThumbColor = TextMuted,
                            uncheckedTrackColor = TextMuted.copy(alpha = 0.3f)
                        )
                    )
                }
            }

            CaseInputField(
                value = state.notes,
                onValueChange = { viewModel.onEvent(CreateCaseEvent.NotesChanged(it)) },
                label = "Private Notes",
                icon = Icons.Default.Note,
                iconColor = SlateBlue,
                modifier = Modifier.alpha(fieldsAnimation.value),
                singleLine = false,
                maxLines = 3
            )

            // File Upload Section
            FileUploadSection(
                modifier = Modifier.alpha(fieldsAnimation.value)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Save Button with animation
            Button(
                onClick = {
                    viewModel.saveOrUpdateCase {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Home.route) { inclusive = true }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .scale(if (fieldsAnimation.value == 1f) 1f else 0.9f),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DeepBlue,
                    contentColor = White,
                    disabledContainerColor = DeepBlue.copy(alpha = 0.3f)
                ),
                enabled = !state.isTitleError && !state.isPhoneError &&
                        state.title.isNotBlank() && state.caseNumber.isNotBlank() &&
                        state.caseType.isNotBlank() && state.courtName.isNotBlank() &&
                        state.clientName.isNotBlank() && state.clientPhone.isNotBlank() &&
                        state.hearingDate != null
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        if (caseId != null) Icons.Default.Edit else Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        if (caseId != null) "Update Case File" else "Save Case File",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }

    // Date Picker Dialog
    if (showDatePicker) {
        AlertDialog(
            onDismissRequest = { showDatePicker = false },
            containerColor = White,
            titleContentColor = DeepBlue,
            title = {
                Text(
                    "Select Date",
                    color = DeepBlue,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                DatePicker(
                    state = datePickerState,
                    colors = DatePickerDefaults.colors(
                        selectedDayContainerColor = DeepBlue,
                        selectedDayContentColor = White,
                        todayDateBorderColor = Emerald,
                        todayContentColor = DeepBlue
                    )
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.onEvent(CreateCaseEvent.DateChanged(datePickerState.selectedDateMillis))
                        showDatePicker = false
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = DeepBlue
                    )
                ) {
                    Text(
                        "Confirm",
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDatePicker = false },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = TextSecondary
                    )
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun SectionHeader(
    title: String,
    icon: ImageVector,
    color: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 8.dp)
    ) {
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
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            title,
            style = MaterialTheme.typography.titleMedium,
            color = color,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 18.sp
        )
    }
}

@Composable
fun CaseInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector? = null,
    iconColor: Color = DeepBlue,
    isError: Boolean = false,
    supportingText: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    maxLines: Int = 1
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
        leadingIcon = icon?.let {
            {
                Icon(
                    it,
                    contentDescription = null,
                    tint = if (isError) Rose else iconColor
                )
            }
        },
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
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        singleLine = singleLine,
        maxLines = maxLines,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        textStyle = MaterialTheme.typography.bodyLarge.copy( // <-- CHANGED HERE (added this line)
            color = DeepBlue, // <-- CHANGED HERE (added this line)
            fontWeight = FontWeight.Normal
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = if (isError) Rose else DeepBlue,
            unfocusedBorderColor = if (isError) Rose.copy(alpha = 0.5f) else BorderLight,
            focusedLabelColor = if (isError) Rose else DeepBlue,
            cursorColor = if (isError) Rose else DeepBlue,
            focusedLeadingIconColor = if (isError) Rose else iconColor,
            unfocusedLeadingIconColor = if (isError) Rose else iconColor.copy(alpha = 0.7f),
            focusedTextColor = DeepBlue,
            unfocusedTextColor = DeepBlue.copy(alpha = 0.7f)


        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaseTypeDropdown(
    selectedType: String,
    options: List<String>,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selectedType,
            onValueChange = {},
            readOnly = true,
            label = {
                Text(
                    "Case Type*",
                    color = if (selectedType.isBlank()) TextMuted else DeepBlue
                )
            },
            trailingIcon = {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .then(Modifier.clickable { expanded = !expanded })
                ) {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                    // The icon color will inherit from the text field's trailing icon color
                }
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = DeepBlue,
                unfocusedBorderColor = BorderLight,
                focusedTrailingIconColor = DeepBlue,
                focusedLabelColor = DeepBlue,
                cursorColor = DeepBlue
            )
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            containerColor = White
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            option,
                            color = if (option == selectedType) DeepBlue else TextPrimary,
                            fontWeight = if (option == selectedType) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    onClick = {
                        onSelect(option)
                        expanded = false
                    },
                    leadingIcon = if (option == selectedType) {
                        {
                            Icon(
                                Icons.Default.Check,
                                contentDescription = null,
                                tint = Emerald,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    } else null
                )
            }
        }
    }
}

@Composable
fun FileUploadSection(
    modifier: Modifier = Modifier
) {
    val animatedScale = remember { Animatable(0.95f) }

    LaunchedEffect(key1 = true) {
        animatedScale.animateTo(
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(1000, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            )
        )
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .scale(animatedScale.value),
        shape = RoundedCornerShape(16.dp),
        color = White,
        border = BorderStroke(1.dp, BorderLight),
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
                .clickable { /* Handle file upload */ },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                color = DeepBlue.copy(alpha = 0.1f),
                shape = CircleShape,
                modifier = Modifier.size(56.dp)
            ) {
                Icon(
                    Icons.Default.UploadFile,
                    contentDescription = null,
                    tint = DeepBlue,
                    modifier = Modifier.padding(16.dp)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                "Upload Documents",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = DeepBlue
            )
            Text(
                "PDF, JPG up to 5MB",
                style = MaterialTheme.typography.labelSmall,
                color = TextMuted
            )
        }
    }
}
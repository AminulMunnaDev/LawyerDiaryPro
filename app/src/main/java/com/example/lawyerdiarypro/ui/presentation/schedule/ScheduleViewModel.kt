package com.example.lawyerdiarypro.ui.presentation.schedule

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.lawyerdiarypro.ui.database.AppDatabase
import com.example.lawyerdiarypro.ui.database.CaseEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.text.SimpleDateFormat
import java.util.Locale

class ScheduleViewModel(application: Application) : AndroidViewModel(application) {
    private val caseDao = AppDatabase.getDatabase(application).caseDao()

    // Observe all cases and group them by Date string
    val groupedCases: StateFlow<Map<String, List<CaseEntity>>> = caseDao.getAllCases()
        .map { list ->
            list.filter { it.hearingDate != 0L }
                .sortedBy { it.hearingDate }
                .groupBy { formatDate(it.hearingDate) }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyMap())

    private fun formatDate(millis: Long): String {
        return SimpleDateFormat("EEEE, dd MMM yyyy", Locale.getDefault()).format(millis)
    }
}
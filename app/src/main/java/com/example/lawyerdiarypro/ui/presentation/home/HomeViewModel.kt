package com.example.lawyerdiarypro.ui.presentation.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.lawyerdiarypro.ui.database.AppDatabase
import com.example.lawyerdiarypro.ui.database.CaseEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn



class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val caseDao = AppDatabase.getDatabase(application).caseDao()

    // .stateIn converts the Flow from Room into a StateFlow that Compose can watch
    val allCases: StateFlow<List<CaseEntity>> = caseDao.getAllCases()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val importantCases: StateFlow<List<CaseEntity>> = caseDao.getImportantCases()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}
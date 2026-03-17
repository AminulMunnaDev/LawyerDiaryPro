package com.example.lawyerdiarypro.ui.presentation.home.Case

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.lawyerdiarypro.ui.database.AppDatabase
import com.example.lawyerdiarypro.ui.database.CaseEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CaseDetailsViewModel(application: Application) : AndroidViewModel(application) {
    private val caseDao = AppDatabase.getDatabase(application).caseDao()
    
    private val _selectedCase = MutableStateFlow<CaseEntity?>(null)
    val selectedCase: StateFlow<CaseEntity?> = _selectedCase
    
    fun loadCase(caseId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val case = caseDao.getCaseById(caseId)
                _selectedCase.value = case
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteCase(context: Context, case: CaseEntity, onDeleted: () -> Unit) {
        viewModelScope.launch {
            try {
                caseDao.deleteCase(case)
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Case deleted successfully", Toast.LENGTH_SHORT).show()
                    onDeleted()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Error deleting case", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

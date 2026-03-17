package com.example.lawyerdiarypro.ui.presentation.home.Case

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.lawyerdiarypro.ui.database.AppDatabase
import com.example.lawyerdiarypro.ui.database.CaseEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class CaseFormState(
    // Mandatory
    val title: String = "",
    val caseNumber: String = "",
    val courtName: String = "",
    val clientName: String = "",
    val clientPhone: String = "",
    val caseType: String = "Civil",
    val hearingDate: Long? = null,

    // Optional Legal
    val filingNumber: String = "",
    val cnrNumber: String = "",
    val partyNameVs: String = "",
    val appearingFor: String = "",
    val judgeName: String = "",
    val opposingAdvocate: String = "",
    val isImportant: Boolean = false,
    val notes: String = "",

    // FIR Details
    val policeStation: String = "",
    val firNumber: String = "",
    val firYear: String = "",
    val actsAndSections: String = "",

    // Validation
    val isTitleError: Boolean = false,
    val isPhoneError: Boolean = false
)

sealed class CreateCaseEvent {
    data class TitleChanged(val value: String) : CreateCaseEvent()
    data class CaseNumberChanged(val value: String) : CreateCaseEvent()
    data class CourtNameChanged(val value: String) : CreateCaseEvent()
    data class ClientNameChanged(val value: String) : CreateCaseEvent()
    data class ClientPhoneChanged(val value: String) : CreateCaseEvent()
    data class TypeChanged(val value: String) : CreateCaseEvent()
    data class DateChanged(val value: Long?) : CreateCaseEvent()

    // Optional events
    data class FilingNumberChanged(val value: String) : CreateCaseEvent()
    data class CnrNumberChanged(val value: String) : CreateCaseEvent()
    data class PartyVsChanged(val value: String) : CreateCaseEvent()
    data class AppearingForChanged(val value: String) : CreateCaseEvent()
    data class JudgeNameChanged(val value: String) : CreateCaseEvent()
    data class OpposingAdvocateChanged(val value: String) : CreateCaseEvent()
    data class ImportanceChanged(val value: Boolean) : CreateCaseEvent()
    data class NotesChanged(val value: String) : CreateCaseEvent()
    data class PoliceStationChanged(val value: String) : CreateCaseEvent()
    data class FirNumberChanged(val value: String) : CreateCaseEvent()
    data class FirYearChanged(val value: String) : CreateCaseEvent()
    data class ActsSectionsChanged(val value: String) : CreateCaseEvent()
}


class CreateCaseViewModel(application: Application) : AndroidViewModel(application) {

    private val caseDao = AppDatabase.getDatabase(application).caseDao()

    var state by mutableStateOf(CaseFormState())
        private set

    private var editingCaseId: Int? = null
    val caseTypes = listOf("Civil", "Criminal", "Family", "Corporate", "Labor")


    fun loadCaseForEditing(id: Int) {
        editingCaseId = id
        viewModelScope.launch {
            caseDao.getCaseById(id)?.let { entity ->
                state = state.copy(
                    title = entity.title,
                    caseNumber = entity.caseNumber,
                    courtName = entity.courtName,
                    clientName = entity.clientName,
                    clientPhone = entity.clientPhone,
                    caseType = entity.caseType,
                    hearingDate = entity.hearingDate,
                    isImportant = entity.isImportant,

                    // MAPPING THE REST OF THE FIELDS
                    filingNumber = entity.filingNumber,
                    cnrNumber = entity.cnrNumber,
                    partyNameVs = entity.partyNameVs,
                    appearingFor = entity.appearingFor,
                    judgeName = entity.judgeName,
                    opposingAdvocate = entity.opposingAdvocate,
                    notes = entity.notes,
                    policeStation = entity.policeStation,
                    firNumber = entity.firNumber,
                    firYear = entity.firYear.toString(),
                    actsAndSections = entity.actsAndSections
                )
            }
        }
    }
    fun saveOrUpdateCase(onSuccess: () -> Unit) {
        if (validateForm()) {
            viewModelScope.launch(Dispatchers.IO) {
                val entity = CaseEntity(
                    id = editingCaseId ?: 0, // If ID is 0, Room inserts; if not, it updates
                    title = state.title,
                    caseNumber = state.caseNumber,
                    courtName = state.courtName,
                    clientName = state.clientName,
                    clientPhone = state.clientPhone,
                    caseType = state.caseType,
                    hearingDate = state.hearingDate ?: 0L,
                    isImportant = state.isImportant,
                    partyNameVs = state.partyNameVs,
                    cnrNumber = state.cnrNumber,
                    filingNumber = state.filingNumber,
                    appearingFor = state.appearingFor,
                    judgeName = state.judgeName,
                    opposingAdvocate = state.opposingAdvocate,
                    notes = state.notes,
                    policeStation = state.policeStation,
                    firNumber = state.firNumber,
                    firYear = state.firYear.toIntOrNull() ?: 0,
                    actsAndSections = state.actsAndSections
                )

                if (editingCaseId == null) {
                    caseDao.insertCase(entity)
                } else {
                    caseDao.updateCase(entity)
                }

                withContext(Dispatchers.Main) { onSuccess() }
            }
        }
    }




    fun onEvent(event: CreateCaseEvent) {
        state = when (event) {
            is CreateCaseEvent.TitleChanged -> state.copy(title = event.value, isTitleError = false)
            is CreateCaseEvent.CaseNumberChanged -> state.copy(caseNumber = event.value)
            is CreateCaseEvent.CourtNameChanged -> state.copy(courtName = event.value)
            is CreateCaseEvent.ClientNameChanged -> state.copy(clientName = event.value)
            is CreateCaseEvent.ClientPhoneChanged -> {
                val isError = event.value.isNotEmpty() && event.value.length < 10
                state.copy(clientPhone = event.value, isPhoneError = isError)
            }

            is CreateCaseEvent.TypeChanged -> state.copy(caseType = event.value)
            is CreateCaseEvent.DateChanged -> state.copy(hearingDate = event.value)
            is CreateCaseEvent.FilingNumberChanged -> state.copy(filingNumber = event.value)
            is CreateCaseEvent.CnrNumberChanged -> state.copy(cnrNumber = event.value)
            is CreateCaseEvent.PartyVsChanged -> state.copy(partyNameVs = event.value)
            is CreateCaseEvent.AppearingForChanged -> state.copy(appearingFor = event.value)
            is CreateCaseEvent.JudgeNameChanged -> state.copy(judgeName = event.value)
            is CreateCaseEvent.OpposingAdvocateChanged -> state.copy(opposingAdvocate = event.value)
            is CreateCaseEvent.ImportanceChanged -> state.copy(isImportant = event.value)
            is CreateCaseEvent.NotesChanged -> state.copy(notes = event.value)
            is CreateCaseEvent.PoliceStationChanged -> state.copy(policeStation = event.value)
            is CreateCaseEvent.FirNumberChanged -> state.copy(firNumber = event.value)
            is CreateCaseEvent.FirYearChanged -> state.copy(firYear = event.value)
            is CreateCaseEvent.ActsSectionsChanged -> state.copy(actsAndSections = event.value)
        }
    }

    fun validateForm(): Boolean {
        val hasErrors = state.title.isBlank() || state.clientName.isBlank() || state.hearingDate == null || state.isPhoneError
        // Trigger specific errors for UI feedback
        state = state.copy(
            isTitleError = state.title.isBlank(),
            isPhoneError = state.clientPhone.isNotEmpty() && state.clientPhone.length < 10
        )
        return !hasErrors
    }

    fun saveCase(onSuccess: () -> Unit) {
        if (validateForm()) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val entity = CaseEntity(
                        title = state.title,
                        caseNumber = state.caseNumber,
                        courtName = state.courtName,
                        clientName = state.clientName,
                        clientPhone = state.clientPhone,
                        caseType = state.caseType,
                        hearingDate = state.hearingDate ?: 0L,
                        isImportant = state.isImportant,
                        partyNameVs = state.partyNameVs,
                        cnrNumber = state.cnrNumber
                    )
                    caseDao.insertCase(caseEntity = entity)
                    withContext(Dispatchers.Main) {
                        onSuccess()
                    }
                } catch (e: Exception) {
                    // Log error if needed
                    e.printStackTrace()
                }
            }
        }
    }
}



package com.example.lawyerdiarypro.ui.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cases")
data class CaseEntity(
    @PrimaryKey(autoGenerate = true) 
    val id: Int = 0,
    val title: String = "",
    val caseNumber: String = "",
    val courtName: String = "",
    val clientName: String = "",
    val clientPhone: String = "",
    val caseType: String = "Civil",
    val hearingDate: Long = 0L,
    val isImportant: Boolean = false,
    val partyNameVs: String = "",
    val cnrNumber: String = "",
    val filingNumber: String = "",
    val appearingFor: String = "",
    val judgeName: String = "",
    val opposingAdvocate: String = "",
    val notes: String = "",
    val policeStation: String = "",
    val firNumber: String = "",
    val firYear: Int = 0,
    val actsAndSections: String = ""
)

package com.example.lawyerdiarypro.ui.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow



@Dao
interface CaseDao {
    @Query("SELECT * FROM cases ORDER BY id DESC")
    fun getAllCases(): Flow<List<CaseEntity>>

    @Query("SELECT * FROM cases WHERE isImportant = 1 ORDER BY id DESC")
    fun getImportantCases(): Flow<List<CaseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    // Rename 'case' to 'caseEntity' to avoid Java keyword conflict
    suspend fun insertCase(caseEntity: CaseEntity)
    @Delete
    suspend fun deleteCase(caseEntity: CaseEntity)

    @Update
    suspend fun updateCase(caseEntity: CaseEntity)

    @Query("SELECT * FROM cases WHERE id = :caseId")
    suspend fun getCaseById(caseId: Int): CaseEntity?
}
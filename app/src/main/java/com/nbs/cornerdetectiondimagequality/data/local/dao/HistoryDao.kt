package com.nbs.cornerdetectiondimagequality.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.nbs.cornerdetectiondimagequality.data.local.entity.HistoryActivity

@Dao
interface HistoryDao {
    @Insert
    suspend fun insertActivity(activity: HistoryActivity)

    @Query("SELECT * FROM HistoryActivity WHERE isSuccess = :success ORDER BY timestamp DESC")
    suspend fun getActivitiesBySuccess(success: Boolean): List<HistoryActivity>

    @Query("SELECT * FROM HistoryActivity WHERE isSuccess = :success AND timestamp BETWEEN :startTime AND :endTime ORDER BY timestamp DESC")
    suspend fun getActivitiesByDateAndSuccess(startTime: Long, endTime: Long, success: Boolean): List<HistoryActivity>

    @Query("SELECT * FROM HistoryActivity ORDER BY timestamp DESC")
    suspend fun getAllActivities(): List<HistoryActivity>
}
package com.nbs.cornerdetectiondimagequality.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.nbs.cornerdetectiondimagequality.data.local.entity.HistoryEntity

@Dao
interface HistoryDao {
    @Insert
    suspend fun insertActivity(activity: HistoryEntity)

    @Query("SELECT * FROM HistoryEntity WHERE isSuccess = :success ORDER BY timestamp DESC LIMIT :limit OFFSET :offset")
    suspend fun getActivitiesBySuccess(success: Boolean,limit: Int, offset: Int): List<HistoryEntity>

    @Query("SELECT * FROM HistoryEntity ORDER BY id ASC LIMIT :limit OFFSET :offset")
    suspend fun getPagedList(limit: Int, offset: Int): List<HistoryEntity>

    @Query("SELECT * FROM HistoryEntity ORDER BY id ASC")
    fun getHistory() : List<HistoryEntity>

    @Query("SELECT * FROM HistoryEntity WHERE id = :id")
    suspend fun getHistoryById(id: Int) : HistoryEntity

}
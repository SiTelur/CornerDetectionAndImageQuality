package com.nbs.cornerdetectiondimagequality.data.local.dao

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.nbs.cornerdetectiondimagequality.data.local.entity.HistoryActivity

@Dao
interface HistoryDao {
    @Insert
    suspend fun insertActivity(activity: HistoryActivity)

    @Query("SELECT * FROM HistoryActivity WHERE isSuccess = :success ORDER BY timestamp DESC  LIMIT :limit OFFSET :offset")
    suspend fun getActivitiesBySuccess(success: Boolean,limit: Int, offset: Int): List<HistoryActivity>

    @Query("SELECT * FROM HistoryActivity ORDER BY id ASC LIMIT :limit OFFSET :offset")
    suspend fun getPagedList(limit: Int, offset: Int): List<HistoryActivity>

    @Query("SELECT * FROM HistoryActivity ORDER BY id ASC")
    fun getHistory() : List<HistoryActivity>
}
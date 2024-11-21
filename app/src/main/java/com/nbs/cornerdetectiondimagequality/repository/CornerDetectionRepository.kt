package com.nbs.cornerdetectiondimagequality.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.nbs.cornerdetectiondimagequality.data.local.dao.HistoryDao
import com.nbs.cornerdetectiondimagequality.data.local.entity.HistoryActivity
import com.nbs.cornerdetectiondimagequality.presentation.component.AllHistoryPagingSource
import com.nbs.cornerdetectiondimagequality.presentation.component.SpecificHistoryPagingSource
import com.nbs.cornerdetectiondimagequality.utils.Session
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class CornerDetectionRepository(
    private val session: Session,
    private val dao: HistoryDao
) {

    val pin: LiveData<String?> = session.getSession().asLiveData()
    val isRegistered: LiveData<Boolean> = session.isRegistered().asLiveData()

    private var _isLogin = MutableLiveData<Boolean>()
    val isLogin: LiveData<Boolean> = _isLogin

    suspend fun saveSession(pin: String) {
        session.savePin(pin)
    }

    suspend fun setRegistered(status: Boolean) {
        session.setRegistered(status)
    }

    fun getAllHistory(): Flow<PagingData<HistoryActivity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                enablePlaceholders = false,
                initialLoadSize = 10
            ),
            pagingSourceFactory = {
                AllHistoryPagingSource(dao)
            }
        ).flow
    }

    fun getSuccessHistory(): Flow<PagingData<HistoryActivity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                enablePlaceholders = false,
                initialLoadSize = 10
            ),
            pagingSourceFactory = {
                SpecificHistoryPagingSource(true, dao)
            }
        ).flow
    }


    fun getFailureHistory(): Flow<PagingData<HistoryActivity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                enablePlaceholders = false,
                initialLoadSize = 10
            ),
            pagingSourceFactory = {
                SpecificHistoryPagingSource(false, dao)
            }
        ).flow
    }

    suspend fun insertHistory(history: HistoryActivity) {
        dao.insertActivity(history)
    }
}
package com.nbs.cornerdetectiondimagequality.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.nbs.cornerdetectiondimagequality.data.local.dao.HistoryDao
import com.nbs.cornerdetectiondimagequality.data.local.entity.HistoryActivity
import com.nbs.cornerdetectiondimagequality.utils.Session

class CornerDetectionRepository(
    private val session: Session,
    private val dao : HistoryDao
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

    fun getAllHistory(): LiveData<List<HistoryActivity>> = dao.getAllActivities()

    fun getSuccessHistory(): LiveData<List<HistoryActivity>> = dao.getActivitiesBySuccess(true)

    fun getFailureHistory(): LiveData<List<HistoryActivity>> = dao.getActivitiesBySuccess(false)

}
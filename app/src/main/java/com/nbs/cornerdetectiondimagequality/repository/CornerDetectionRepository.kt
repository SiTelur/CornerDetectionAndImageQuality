package com.nbs.cornerdetectiondimagequality.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.nbs.cornerdetectiondimagequality.presentation.auth.Session

class CornerDetectionRepository(
    private val session: Session
) {

    val pin: LiveData<String?> = session.getSession().asLiveData()
    val isRegistered: LiveData<Boolean> = session.isRegistered().asLiveData()

    private var _isLogin = MutableLiveData<Boolean>()
    val isLogin: LiveData<Boolean> = _isLogin

    suspend fun saveSession(pin: String) {
        session.saveSession(pin)
    }

    suspend fun setRegistered(status: Boolean) {
        session.setRegistered(status)
    }
}
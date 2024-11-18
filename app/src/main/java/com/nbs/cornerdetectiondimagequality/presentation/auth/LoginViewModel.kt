package com.nbs.cornerdetectiondimagequality.presentation.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class LoginViewModel(private val session: Session): ViewModel() {

    val pin: LiveData<String?> = session.getSession().asLiveData()

    fun saveSession(pin: String) {
        viewModelScope.launch {
            session.saveSession(pin)
        }
    }
}
package com.nbs.cornerdetectiondimagequality.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nbs.cornerdetectiondimagequality.repository.CornerDetectionRepository
import kotlinx.coroutines.launch


class AuthViewModel(private val repository: CornerDetectionRepository): ViewModel() {

    val pin: LiveData<String?> = repository.pin
    val isRegistered: LiveData<Boolean> = repository.isRegistered

    fun savePin(pin: String) {
        viewModelScope.launch {
            repository.saveSession(pin)
            repository.setRegistered(true)
        }
    }
}
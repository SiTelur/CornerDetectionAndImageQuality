package com.nbs.cornerdetectiondimagequality.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nbs.cornerdetectiondimagequality.data.local.entity.HistoryActivity
import com.nbs.cornerdetectiondimagequality.repository.CornerDetectionRepository
import kotlinx.coroutines.launch


class CameraViewModel(private val repository: CornerDetectionRepository) : ViewModel() {

    fun insertData(data : HistoryActivity) {
        viewModelScope.launch{
            repository.insertHistory(data)
        }
    }
}
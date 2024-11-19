package com.nbs.cornerdetectiondimagequality.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.nbs.cornerdetectiondimagequality.repository.CornerDetectionRepository

class DashboardViewModel(private val repository: CornerDetectionRepository): ViewModel() {
    val getAllData = repository.getAllHistory()
    val getSuccessData = repository.getAllHistory()
    val getFailedData = repository.getAllHistory()
}
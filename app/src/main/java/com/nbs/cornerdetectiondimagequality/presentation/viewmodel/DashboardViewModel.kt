package com.nbs.cornerdetectiondimagequality.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nbs.cornerdetectiondimagequality.data.local.entity.HistoryActivity
import com.nbs.cornerdetectiondimagequality.repository.CornerDetectionRepository

class DashboardViewModel(repository: CornerDetectionRepository): ViewModel() {


    val getAllHistory: LiveData<PagingData<HistoryActivity>> =
        repository.getAllHistory().cachedIn(viewModelScope)

    val getSuccessHistory: LiveData<PagingData<HistoryActivity>> =
        repository.getSuccessHistory().cachedIn(viewModelScope)

    val getFailureHistory: LiveData<PagingData<HistoryActivity>> = repository.getFailureHistory().cachedIn(viewModelScope)

}
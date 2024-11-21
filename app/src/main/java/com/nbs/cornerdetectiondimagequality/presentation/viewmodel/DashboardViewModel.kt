package com.nbs.cornerdetectiondimagequality.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nbs.cornerdetectiondimagequality.data.local.entity.HistoryActivity
import com.nbs.cornerdetectiondimagequality.repository.CornerDetectionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class DashboardViewModel(val repository: CornerDetectionRepository): ViewModel() {

    val getAllHistory: Flow<PagingData<HistoryActivity>> =
        repository.getAllHistory().flowOn(Dispatchers.IO).cachedIn(viewModelScope)

    val getSuccessHistory =
        repository.getSuccessHistory().flowOn(Dispatchers.IO).cachedIn(viewModelScope)

    val getFailureHistory: Flow<PagingData<HistoryActivity>> = repository.getFailureHistory().flowOn(
        Dispatchers.IO).cachedIn(viewModelScope)

}
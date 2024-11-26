package com.nbs.cornerdetectiondimagequality.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nbs.cornerdetectiondimagequality.data.Resource
import com.nbs.cornerdetectiondimagequality.data.local.entity.HistoryActivity
import com.nbs.cornerdetectiondimagequality.repository.CornerDetectionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class DashboardViewModel(val repository: CornerDetectionRepository): ViewModel() {

    private var _allHistory : MutableStateFlow<Resource<PagingData<HistoryActivity>>> = MutableStateFlow(Resource.Idle)
    val allHistory : StateFlow<Resource<PagingData<HistoryActivity>>> = _allHistory.asStateFlow()

    init {
        getAllHistory()

    }

    fun getAllHistory() {
        viewModelScope.launch {
            _allHistory.value = Resource.Loading
            repository.getAllHistory()
                .catch { cause: Throwable ->
                    _allHistory.value = Resource.Error("Landmark tidak diketahui")
                }
                .collect { history ->
                    _allHistory.value = Resource.Success(history)
                }
        }
    }


        fun getSuccessHistory() =
            repository.getSuccessHistory().flowOn(Dispatchers.IO).cachedIn(viewModelScope)

        val getFailureHistory: Flow<PagingData<HistoryActivity>> =
            repository.getFailureHistory().flowOn(
                Dispatchers.IO
            ).cachedIn(viewModelScope)


}

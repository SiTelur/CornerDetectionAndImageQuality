package com.nbs.cornerdetectiondimagequality.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nbs.cornerdetectiondimagequality.data.Resource
import com.nbs.cornerdetectiondimagequality.data.local.entity.HistoryEntity
import com.nbs.cornerdetectiondimagequality.repository.CornerDetectionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class DashboardViewModel(private val repository: CornerDetectionRepository): ViewModel() {

    private var _allHistory : MutableStateFlow<Resource<PagingData<HistoryEntity>>> = MutableStateFlow(Resource.Idle)
    val allHistory : StateFlow<Resource<PagingData<HistoryEntity>>> = _allHistory.asStateFlow()

    init {
        getAllHistory()

    }

    private fun getAllHistory() {
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

    val getFailureHistory: Flow<PagingData<HistoryEntity>> =
        repository.getFailureHistory().flowOn(
            Dispatchers.IO
        ).cachedIn(viewModelScope)
}

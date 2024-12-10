package com.nbs.cornerdetectiondimagequality.presentation.component.paging3

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nbs.cornerdetectiondimagequality.data.local.dao.HistoryDao
import com.nbs.cornerdetectiondimagequality.data.local.entity.HistoryEntity
import kotlinx.coroutines.delay

class SpecificHistoryPagingSource(private val condition : Boolean,private val dao : HistoryDao) : PagingSource<Int, HistoryEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HistoryEntity> {
        val page = params.key ?: 0

        return try {
            Log.d("MainPagingSource", "load: $page")
            val entities = dao.getActivitiesBySuccess(condition,params.loadSize, page * params.loadSize)
            if (page != 0) delay(1000)
            LoadResult.Page(
                data = entities,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (entities.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, HistoryEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
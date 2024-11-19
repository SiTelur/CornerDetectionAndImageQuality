package com.nbs.cornerdetectiondimagequality.di

import android.content.Context
import com.nbs.cornerdetectiondimagequality.data.local.room.HistoryDatabase
import com.nbs.cornerdetectiondimagequality.utils.Session
import com.nbs.cornerdetectiondimagequality.utils.session
import com.nbs.cornerdetectiondimagequality.repository.CornerDetectionRepository

object Injection {
    fun provideRepository(context: Context) : CornerDetectionRepository {
        val session = Session.getInstance(context.session)
        val database = HistoryDatabase.getDatabase(context)
        val dao = database.historyDao()
        return CornerDetectionRepository(session,dao)
    }
}
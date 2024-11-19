package com.nbs.cornerdetectiondimagequality.di

import android.content.Context
import com.nbs.cornerdetectiondimagequality.presentation.auth.Session
import com.nbs.cornerdetectiondimagequality.presentation.auth.session
import com.nbs.cornerdetectiondimagequality.repository.CornerDetectionRepository

object Injection {
    fun provideRepository(context: Context) : CornerDetectionRepository {
        val session = Session.getInstance(context.session)

        return CornerDetectionRepository(session)
    }
}
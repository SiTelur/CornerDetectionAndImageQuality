package com.nbs.cornerdetectiondimagequality.presentation.auth

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.session: DataStore<Preferences> by preferencesDataStore(name = "sessions")

class Session private constructor(private val session: DataStore<Preferences>) {

    private val SESSION_KEY = stringPreferencesKey("session_key")

    fun getSession(): Flow<String?> {
        return session.data
            .map { preferences -> preferences[SESSION_KEY] }
    }

    suspend fun saveSession(pin: String) {
        session.edit { preferences ->
            preferences[SESSION_KEY] = pin
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: Session? = null

        fun getInstance(session: DataStore<Preferences>): Session {
            return INSTANCE ?: synchronized(this) {
                val instance = Session(session)
                INSTANCE = instance
                instance
            }
        }
    }

}
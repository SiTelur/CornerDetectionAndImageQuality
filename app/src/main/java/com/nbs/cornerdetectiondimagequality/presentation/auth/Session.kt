package com.nbs.cornerdetectiondimagequality.presentation.auth

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.session: DataStore<Preferences> by preferencesDataStore(name = "sessions")

class Session private constructor(private val session: DataStore<Preferences>) {

    private val SESSION_KEY = stringPreferencesKey("session_key")
    private val IS_REGISTERED = booleanPreferencesKey("is_registered")

    fun getSession(): Flow<String?> {
        return session.data
            .map { preferences -> preferences[SESSION_KEY] }
    }

    suspend fun saveSession(pin: String) {
        session.edit { preferences ->
            preferences[SESSION_KEY] = pin
        }
    }

    fun isRegistered(): Flow<Boolean> {
        return session.data
            .map { preferences -> preferences[IS_REGISTERED] ?: false }
    }

    suspend fun setRegistered(status: Boolean) {
        session.edit { preferences ->
            preferences[IS_REGISTERED] = status
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
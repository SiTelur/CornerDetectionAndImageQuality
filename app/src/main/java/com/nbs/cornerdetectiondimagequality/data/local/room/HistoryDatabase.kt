package com.nbs.cornerdetectiondimagequality.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.nbs.cornerdetectiondimagequality.data.local.dao.HistoryDao
import com.nbs.cornerdetectiondimagequality.data.local.entity.HistoryActivity
import com.nbs.cornerdetectiondimagequality.utils.RoomDateConverter

@Database(entities = [HistoryActivity::class], version = 1, exportSchema = false)
@TypeConverters(RoomDateConverter::class)
abstract class HistoryDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao

    companion object {
        @Volatile
        private var INSTANCE: HistoryDatabase?  = null

        fun getDatabase(context:Context) : HistoryDatabase {
            if (INSTANCE == null) {
                synchronized(HistoryDatabase::class.java){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        HistoryDatabase::class.java,"history_database")
                        .build()
                }
            }
            return INSTANCE as HistoryDatabase
        }
    }
}
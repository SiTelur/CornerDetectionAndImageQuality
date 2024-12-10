package com.nbs.cornerdetectiondimagequality.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.nbs.cornerdetectiondimagequality.utils.RoomDateConverter
import java.time.LocalDateTime

@Entity
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val pictureUri: String,
    val score: Double,
    @TypeConverters(RoomDateConverter::class)
    val timestamp: LocalDateTime,
    val isSuccess: Boolean
)
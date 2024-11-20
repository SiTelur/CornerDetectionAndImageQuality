package com.nbs.cornerdetectiondimagequality.utils

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

class RoomDateConverter {

    @TypeConverter
    fun fromLocalDateTime(dateTime: LocalDateTime?): Long? {
        return dateTime?.atZone(ZoneId.systemDefault())?.toInstant()?.toEpochMilli()
    }
    @TypeConverter
    fun toLocalDateTime(timestamp: Long?): LocalDateTime? {
        return timestamp?.let {
            LocalDateTime.ofInstant(
                Instant.ofEpochMilli(it),
                ZoneId.systemDefault()
            )
        }
    }

    fun getCurrentLocalDateTime(): LocalDateTime {
        return LocalDateTime.now(ZoneId.systemDefault())
    }

    fun getCurrentZoneId(): ZoneId {
        return ZoneId.systemDefault()
    }
}
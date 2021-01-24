package com.jerryhanks.farimoneytest.data.db.converters

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeParseException

/**
 * Author: Jerry Okafor
 * Project: TestApp
 * <p>
 * Date: 22/01/2021
 * <p>
 */

object DateConverter {
    @TypeConverter
    @JvmStatic
    fun toLocalDateTime(value: String?): LocalDateTime? {
        return try {
            value?.let {
                LocalDateTime.parse(it)
            }
        } catch (e: DateTimeParseException) {
            return LocalDateTime.now()
        }
    }

    @TypeConverter
    @JvmStatic
    fun fromLocalDateTime(date: LocalDateTime?): String {
        return date.toString()
    }
}
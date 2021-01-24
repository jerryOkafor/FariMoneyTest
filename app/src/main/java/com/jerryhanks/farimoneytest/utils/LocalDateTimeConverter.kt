package com.jerryhanks.farimoneytest.utils

import com.google.gson.*
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Author: Jerry
 * Project: FairmoneyTest
 */
class LocalDateTimeConverter : JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

    override fun serialize(
        src: LocalDateTime?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LocalDateTime {
        return json?.asString?.let { LocalDateTime.parse(it, DateTimeFormatter.ISO_DATE_TIME) }
            ?: LocalDateTime.now()
    }
}
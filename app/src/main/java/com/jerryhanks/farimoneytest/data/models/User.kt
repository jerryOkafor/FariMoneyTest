package com.jerryhanks.farimoneytest.data.models

import androidx.room.Embedded
import com.jerryhanks.farimoneytest.data.db.entities.LocationEntity
import com.jerryhanks.farimoneytest.data.db.entities.UserEntity
import java.time.LocalDateTime

/**
 * Author: Jerry
 * Project: FairmoneyTest
 */
data class Location(
    val timezone: String? = "",
    val city: String? = "",
    val country: String? = "",
    val state: String? = "",
    val street: String? = ""
) {
    fun toLocationEntity() = LocationEntity(timezone, city, country, state, street)

    companion object {
        fun fromLocationEntity(locationEntity: LocationEntity?) =
            Location(
                locationEntity?.timezone,
                locationEntity?.city,
                locationEntity?.country,
                locationEntity?.state,
                locationEntity?.street
            )
    }
}

data class User(
    val id: String = "",
    val dateOfBirth: LocalDateTime? = null,
    val gender: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val title: String = "",
    val email: String = "",
    val registerDate: LocalDateTime? = null,
    val phone: String = "",
    val picture: String = "",

    @Embedded
    val location: Location? = null
) {
    fun isDetailFetched() =
        dateOfBirth != null && registerDate != null && phone.isNotBlank() && gender.isNotBlank()

    fun toUserEntity(): UserEntity {
        return UserEntity(
            id,
            dateOfBirth,
            gender,
            firstName,
            lastName,
            title,
            email,
            registerDate,
            phone,
            picture,
            locationEntity = location?.toLocationEntity()
        )
    }

}


data class UserMinimal(
    val id: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val title: String = "",
    val email: String = "",
    val picture: String = "",
) {
    fun toUserEntity(): UserEntity = UserEntity(
        id = id,
        firstName = firstName,
        lastName = lastName,
        title = title,
        email = email,
        picture = picture,
    )
}

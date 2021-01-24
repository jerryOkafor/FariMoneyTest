package com.jerryhanks.farimoneytest.data.db.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jerryhanks.farimoneytest.data.models.Location
import com.jerryhanks.farimoneytest.data.models.User
import java.time.LocalDateTime

/**
 * Author: Jerry
 * Project: FairmoneyTest
 */

data class LocationEntity(
    val timezone: String? = "",
    val city: String? = "",
    val country: String? = "",
    val state: String? = "",
    val street: String? = ""
)

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val id: String,
    val dateOfBirth: LocalDateTime? = null,
    val gender: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val title: String = "",
    val email: String = "",
    val registerDate: LocalDateTime? = null,
    val phone: String = "",
    val picture: String = "",

    @Embedded(prefix = "location_")
    val locationEntity: LocationEntity? = null
) {
    constructor() : this("", null, "", "", "", "", "", null, "", "")

    fun toUser(): User {
        return User(
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
            location = Location.fromLocationEntity(locationEntity)
        )
    }
}

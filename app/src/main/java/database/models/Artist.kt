package database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "artists")
data class Artist(
    @PrimaryKey
    val id: String,
    val roleId: String,
    val countryId: String,
    val slug: String,
    val photo: String,
    val bornAt: String,
    val diedAt: String?,
    val fullName: String,
    val biography: String,
)

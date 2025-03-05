package database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "artists")
data class Artist(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "role_id")
    val roleId: String,
    @ColumnInfo(name = "country_id")
    val countryId: String,
    val slug: String,
    val photo: String,
    @ColumnInfo(name = "born_at")
    val bornAt: String,
    @ColumnInfo(name = "died_at")
    val diedAt: String?,
    @ColumnInfo(name = "full_name")
    val fullName: String,
    val biography: String,
)

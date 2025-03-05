package database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "countries")
data class Country(
    @PrimaryKey
    val id: String,
    val slug: String,
    val title: String,
)

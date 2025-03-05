package database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genres")
data class Genre(
    @PrimaryKey
    val id: String,
    val slug: String,
    val title: String,
)

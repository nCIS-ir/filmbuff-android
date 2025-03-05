package database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "languages")
data class Language(
    @PrimaryKey
    val id: String,
    val code: String,
    val name: String,
)

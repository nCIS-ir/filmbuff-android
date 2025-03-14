package database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Sessions")
data class Session(
    @PrimaryKey
    val token: String,
    val refresh: String,
)

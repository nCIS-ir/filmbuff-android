package database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "roles")
data class Role(
    @PrimaryKey
    val id: String,
    val slug: String,
    val title: String,
)

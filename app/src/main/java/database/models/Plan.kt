package database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plans")
data class Plan(
    @PrimaryKey
    val id: String,
    val slug: String,
    val duration: Int,
    val price: Int,
    val title: String,
)

package database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "activities")
data class Activity(
    @PrimaryKey
    val id: String,
    val slug: String,
    val coins: Int,
    val title: String,
    val description: String,
)

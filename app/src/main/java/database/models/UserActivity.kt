package database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userActivities")
data class UserActivity(
    @PrimaryKey
    val id: String,
    val userId: String,
    val activityId: String,
    val coins: Int,
    val doneAt: String,
)

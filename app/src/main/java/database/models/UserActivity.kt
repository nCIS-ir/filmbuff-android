package database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_activities")
data class UserActivity(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "activity_id")
    val activityId: String,
    val coins: Int,
    @ColumnInfo(name = "done_at")
    val doneAt: String,
)

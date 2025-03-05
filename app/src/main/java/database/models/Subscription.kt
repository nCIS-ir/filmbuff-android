package database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subscriptions")
data class Subscription(
    @PrimaryKey
    val id: String,
    val userId: String,
    val planId: String,
    val price: Int,
    val startedAt: String,
    val endedAt: String,
)

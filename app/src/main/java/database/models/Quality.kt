package database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "qualities")
data class Quality(
    @PrimaryKey
    val id: String,
    val slug: String,
    var isVip: Boolean,
    val title: String,
)

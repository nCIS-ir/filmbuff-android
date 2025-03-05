package database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "qualities")
data class Quality(
    @PrimaryKey
    val id: String,
    val slug: String,
    @ColumnInfo(name = "is_vip")
    val isVip: Boolean,
    val title: String,
)

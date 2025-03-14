package database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "seasons")
data class Season(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "serie_id")
    val serieId: String,
    val slug: String,
    val rating: Float,
    val visits: Int,
    val cover: String,
    val thumbnail: String,
    val trailer: String,
    @ColumnInfo(name = "started_at")
    val startedAt: String,
    @ColumnInfo(name = "ended_at")
    val endedAt: String?,
    @ColumnInfo(name = "added_at")
    val addedAt: String,
    val title: String,
    val description: String,
)

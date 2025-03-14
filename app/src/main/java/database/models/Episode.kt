package database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "episodes")
data class Episode(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "season_id")
    val seasonId: String,
    val slug: String,
    val duration: Int,
    val rating: Float,
    val visits: Int,
    val cover: String,
    val thumbnail: String,
    val trailer: String,
    @ColumnInfo(name = "added_at")
    val addedAt: String,
    val title: String,
    val description: String,
)

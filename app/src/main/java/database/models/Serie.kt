package database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "series")
data class Serie(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "country_id")
    val countryId: String,
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
    @ColumnInfo(name = "is_vip")
    val isVip: Boolean,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean,
    val title: String,
    val description: String,
    val genres: ArrayList<String>,
    val casts: ArrayList<SerieCast>,
)

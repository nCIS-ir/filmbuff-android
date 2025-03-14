package database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "country_id")
    val countryId: String,
    val slug: String,
    val duration: Int,
    val year: Int,
    val rating: Float,
    val visits: Int,
    val cover: String,
    val thumbnail: String,
    val trailer: String,
    @ColumnInfo(name = "added_at")
    val addedAt: String,
    @ColumnInfo(name = "is_vip")
    val isVip: Boolean,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean,
    val title: String,
    val description: String,
    val genres: ArrayList<String>,
    val casts: ArrayList<MovieCast>,
)

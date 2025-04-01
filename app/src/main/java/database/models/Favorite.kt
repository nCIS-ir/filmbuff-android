package database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Favorites")
data class Favorite(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "movie_id")
    val movieId: String?,
    @ColumnInfo(name = "serie_id")
    val serieId: String?,
)

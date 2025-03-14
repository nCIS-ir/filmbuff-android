package database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_casts")
data class MovieCast(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "movie_id")
    val movieId: String,
    @ColumnInfo(name = "artist_id")
    val artistId: String,
    @ColumnInfo(name = "role_id")
    val roleId: String,
)

package database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_files")
data class MovieFile(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "movie_id")
    val movieId: String,
    @ColumnInfo(name = "quality_id")
    val qualityId: String,
    val path: String,
    val audios:  String,
    val subtitles: String,
)

package database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comments")
data class Comment(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "user_id")
    val userId: String,
    @ColumnInfo(name = "movie_id")
    val movieId: String?,
    @ColumnInfo(name = "serie_id")
    val serieId: String?,
    val score: Int,
    val content: String,
    @ColumnInfo(name = "submitted_at")
    val submittedAt: String,
)

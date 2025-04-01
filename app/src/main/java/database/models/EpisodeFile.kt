package database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "episode_files")
data class EpisodeFile(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "episode_id")
    val episodeId: String,
    @ColumnInfo(name = "quality_id")
    val qualityId: String,
    val path: String,
    val audios: String,
    val subtitles: String,
)

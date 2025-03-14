package database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "serie_casts")
data class SerieCast(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "serie_id")
    val serieId: String,
    @ColumnInfo(name = "artist_id")
    val artistId: String,
    @ColumnInfo(name = "role_id")
    val roleId: String,
)

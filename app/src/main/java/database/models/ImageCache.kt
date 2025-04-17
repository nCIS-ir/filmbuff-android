package database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "image_caches")
data class ImageCache(
    @PrimaryKey
    var url: String,
    var photo: String?,
)
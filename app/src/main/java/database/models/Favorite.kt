package database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Favorites")
data class Favorite(
    @PrimaryKey
    val id: String,
    val movie: Movie?,
    val serie: Serie?,
)

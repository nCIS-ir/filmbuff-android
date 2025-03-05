package database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "packs")
data class Pack(
    @PrimaryKey
    val id: String,
    val slug: String,
    val coins: Int,
    val price: Int,
    val title: String,
)

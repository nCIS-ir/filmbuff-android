package database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "purchases")
data class Purchase(
    @PrimaryKey
    val id: String,
    val userId: String,
    val packId: String,
    val authority: String,
    val reference: String,
    var paidAt: String?,
)

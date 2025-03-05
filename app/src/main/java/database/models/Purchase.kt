package database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "purchases")
data class Purchase(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "user_id")
    val userId: String,
    @ColumnInfo(name = "pack_id")
    val packId: String,
    val authority: String,
    val reference: String,
    @ColumnInfo(name = "paid_at")
    val paidAt: String?,
)

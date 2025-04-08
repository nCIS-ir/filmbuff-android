package database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import retrofit.models.Quality as WebQuality

@Entity(tableName = "qualities")
data class Quality(
    @PrimaryKey
    val id: String,
    val slug: String,
    val title: String,
    @ColumnInfo(name = "is_vip")
    val isVip: Boolean,
) {
    companion object {
        fun from(item: WebQuality): Quality {
            return Quality(
                id = item.id,
                slug = item.slug,
                title = item.title,
                isVip = item.isVip,
            )
        }

        fun from(items: List<WebQuality>): List<Quality> {
            val result = mutableListOf<Quality>()
            items.forEach { item -> result.add(from(item)) }
            return result.toList()
        }
    }
}

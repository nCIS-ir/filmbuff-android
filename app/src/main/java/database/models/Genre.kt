package database.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import retrofit.models.BaseGenre

@Entity(tableName = "genres")
data class Genre(
    @PrimaryKey
    val id: String,
    val slug: String,
    val title: String,
) {
    companion object {
        fun from(item: BaseGenre): Genre {
            return Genre(
                id = item.id,
                slug = item.slug,
                title = item.title,
            )
        }

        fun from(items: List<BaseGenre>): List<Genre> {
            val result = mutableListOf<Genre>()
            items.forEach { item -> result.add(from(item)) }
            return result.toList()
        }
    }

}

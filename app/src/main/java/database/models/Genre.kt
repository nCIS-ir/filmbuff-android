package database.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import retrofit.models.Genre as WebGenre

@Entity(tableName = "genres")
data class Genre(
    @PrimaryKey
    val id: String,
    val slug: String,
    val title: String,
) {
    companion object {
        fun from(item: WebGenre): Genre {
            return Genre(
                id = item.id,
                slug = item.slug,
                title = item.title,
            )
        }

        fun from(items: List<WebGenre>): List<Genre> {
            val result = mutableListOf<Genre>()
            items.forEach { item -> result.add(from(item)) }
            return result.toList()
        }
    }

}

package database.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import retrofit.models.Role as WebRole

@Entity(tableName = "roles")
data class Role(
    @PrimaryKey
    val id: String,
    val slug: String,
    val title: String,
) {
    companion object {
        fun from(item: WebRole): Role {
            return Role(
                id = item.id,
                slug = item.slug,
                title = item.title,
            )
        }

        fun from(items: List<WebRole>): List<Role> {
            val result = mutableListOf<Role>()
            items.forEach { item -> result.add(from(item)) }
            return result.toList()
        }
    }
}

package database.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import retrofit.models.BaseActivity

@Entity(tableName = "activities")
data class Activity(
    @PrimaryKey
    val id: String,
    val slug: String,
    val coins: Int,
    val title: String,
    val description: String,
) {
    companion object {
        fun from(item: BaseActivity): Activity {
            return Activity(
                id = item.id,
                slug = item.slug,
                coins = item.coins,
                title = item.title,
                description = item.description
            )
        }

        fun from(items: List<BaseActivity>): List<Activity> {
            val result = mutableListOf<Activity>()
            items.forEach { item -> result.add(from(item)) }
            return result.toList()
        }
    }
}

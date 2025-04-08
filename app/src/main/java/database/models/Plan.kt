package database.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import retrofit.models.Plan as WebPlan

@Entity(tableName = "plans")
data class Plan(
    @PrimaryKey
    val id: String,
    val slug: String,
    val duration: Int,
    val price: Int,
    val title: String,
) {
    companion object {
        fun from(item: WebPlan): Plan {
            return Plan(
                id = item.id,
                slug = item.slug,
                duration = item.duration,
                price = item.price,
                title = item.title,
            )
        }

        fun from(items: List<WebPlan>): List<Plan> {
            val result = mutableListOf<Plan>()
            items.forEach { item -> result.add(from(item)) }
            return result.toList()
        }
    }
}

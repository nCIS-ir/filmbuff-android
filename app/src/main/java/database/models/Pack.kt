package database.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import retrofit.models.Pack as WebPack

@Entity(tableName = "packs")
data class Pack(
    @PrimaryKey
    val id: String,
    val slug: String,
    val coins: Int,
    val price: Int,
    val title: String,
) {
    companion object {
        fun from(item: WebPack): Pack {
            return Pack(
                id = item.id,
                slug = item.slug,
                coins = item.coins,
                price = item.price,
                title = item.title,
            )
        }

        fun from(items: List<WebPack>): List<Pack> {
            val result = mutableListOf<Pack>()
            items.forEach { item -> result.add(from(item)) }
            return result.toList()
        }
    }
}

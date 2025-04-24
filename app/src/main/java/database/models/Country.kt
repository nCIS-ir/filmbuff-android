package database.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import retrofit.models.Country as WebCountry

@Entity(tableName = "countries")
data class Country(
    @PrimaryKey
    val id: String,
    val slug: String,
    val title: String,
    val flag: String,
) {
    companion object {
        fun from(item: WebCountry): Country {
            return Country(
                id = item.id,
                slug = item.slug,
                title = item.title,
                flag = item.flag
            )
        }

        fun from(items: List<WebCountry>): List<Country> {
            val result = mutableListOf<Country>()
            items.forEach { item -> result.add(from(item)) }
            return result.toList()
        }
    }
}

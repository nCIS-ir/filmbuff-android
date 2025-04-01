package database.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import retrofit.models.BaseCountry

@Entity(tableName = "countries")
data class Country(
    @PrimaryKey
    val id: String,
    val slug: String,
    val title: String,
) {
    companion object {
        fun from(item: BaseCountry): Country {
            return Country(
                id = item.id,
                slug = item.slug,
                title = item.title,
            )
        }

        fun from(items: List<BaseCountry>): List<Country> {
            val result = mutableListOf<Country>()
            items.forEach { item -> result.add(from(item)) }
            return result.toList()
        }
    }
}

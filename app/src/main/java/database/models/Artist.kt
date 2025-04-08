package database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import retrofit.models.Artist as WebArtist

@Entity(tableName = "artists")
data class Artist(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "role_id")
    val roleId: String,
    @ColumnInfo(name = "country_id")
    val countryId: String,
    val slug: String,
    val photo: String,
    @ColumnInfo(name = "born_at")
    val bornAt: String,
    @ColumnInfo(name = "died_at")
    val diedAt: String?,
    @ColumnInfo(name = "full_name")
    val fullName: String,
    val biography: String,
) {
    companion object {
        fun from(item: WebArtist): Artist {
            return Artist(
                id = item.id,
                roleId = item.roleId,
                countryId = item.countryId,
                slug = item.slug,
                photo = item.photo,
                bornAt = item.bornAt,
                diedAt = item.diedAt,
                fullName = item.fullName,
                biography = item.biograghy,
            )
        }

        fun from(items: List<WebArtist>): List<Artist> {
            val result = mutableListOf<Artist>()
            items.forEach { item -> result.add(from(item)) }
            return result.toList()
        }
    }
}

package retrofit.models

import com.google.gson.annotations.SerializedName

data class MovieFull(
    val id: String,
    @SerializedName("country_id")
    val countryId: String,
    val slug: String,
    val title: String,
    val description: String,
    val duration: String,
    val year: Int,
    val rating: Float,
    val visits: Int,
    val cover: String,
    val thumbnail: String,
    val trailer: String,
    @SerializedName("added_at")
    val addedAt: String,
    val genres: List<String>,
    val casts: List<MovieCast>,
    @SerializedName("is_vip")
    val isVip: Boolean,
    @SerializedName("is_favorite")
    val isFavorite: Boolean,
)

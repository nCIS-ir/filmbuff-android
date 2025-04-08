package retrofit.models

import com.google.gson.annotations.SerializedName

data class MovieCast(
    val id: String,
    @SerializedName("movie_id")
    val movieId: String,
    @SerializedName("artist_id")
    val artistId: String,
    @SerializedName("role_id")
    val roleId: String,
    val sort: Int,
)

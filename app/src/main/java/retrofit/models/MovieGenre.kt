package retrofit.models

import com.google.gson.annotations.SerializedName

data class MovieGenre(
    @SerializedName("genre_id")
    val genreId: String,
    val movies: List<MovieBrief>
)

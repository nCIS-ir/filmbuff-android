package retrofit.models

import com.google.gson.annotations.SerializedName

data class SerieGenre(
    @SerializedName("genre_id")
    val genreId: String,
    val series: List<SerieBrief>,
)

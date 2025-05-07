package retrofit.models

data class Favorite(
    val id: String,
    val movie: List<MovieBrief>?,
    val serie: List<SerieBrief>?,
)


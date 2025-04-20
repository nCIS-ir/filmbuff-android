package retrofit.models

data class Favorite(
    val id: String,
    val movie: List<Movie>,
    val serie: List<Serie>,
)


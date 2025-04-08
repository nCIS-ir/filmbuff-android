package retrofit.models

data class Plan(
    val id: String,
    val slug: String,
    val duration: Int,
    val price: Int,
    val title: String,
)
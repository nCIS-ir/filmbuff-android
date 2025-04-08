package retrofit.models

data class User(
    val id: String,
    val username: String,
    val coins: Int,
    val email: String,
    val subscription: Int,
)
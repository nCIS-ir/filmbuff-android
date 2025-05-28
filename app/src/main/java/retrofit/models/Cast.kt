package retrofit.models

import com.google.gson.annotations.SerializedName

data class Cast(
    val id: String,
    @SerializedName("role_id")
    val roleId: String,
    val artist: Artist,
    val name: String,
    val description: String,
)

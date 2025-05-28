package retrofit.models

import com.google.gson.annotations.SerializedName

data class Artist(
    val id: String,
    @SerializedName("role_id")
    val roleId: String,
    @SerializedName("country_id")
    val countryId: String,
    val slug: String,
    val photo: String,
    @SerializedName("born_at")
    val bornAt: String,
    @SerializedName("died_at")
    val diedAt: String?,
    @SerializedName("full_name")
    val fullName: String,
    val biography: String,
)

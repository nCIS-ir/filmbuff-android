package retrofit.models

import com.google.gson.annotations.SerializedName

data class SerieCast(
    val id: String,
    @SerializedName("serie_id")
    val serieId: String,
    @SerializedName("artist_id")
    val artistId: String,
    @SerializedName("role_id")
    val roleId: String,
    val sort: Int,
)

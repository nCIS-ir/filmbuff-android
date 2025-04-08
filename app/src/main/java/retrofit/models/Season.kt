package retrofit.models

import com.google.gson.annotations.SerializedName

data class Season(
    val id: String,
    @SerializedName("serie_id")
    val serieId: String,
    val slug: String,
    val title: String,
    val description: String,
    val rating: Float,
    val visits: Int,
    val cover: String,
    val thumbnail: String,
    val trailer: String,
    @SerializedName("started_at")
    val startedAt: String,
    @SerializedName("ended_at")
    val endedAt: String?,
    @SerializedName("added_at")
    val addedAt: String,
)

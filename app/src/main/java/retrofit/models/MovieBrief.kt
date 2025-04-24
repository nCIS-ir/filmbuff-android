package retrofit.models

import com.google.gson.annotations.SerializedName

data class MovieBrief(
    val id: String,
    val slug: String,
    val title: String,
    val year: Int,
    val rating: Float,
    val visits: Int,
    val cover: String,
    val thumbnail: String,
    @SerializedName("is_vip")
    val isVip: Boolean,
)

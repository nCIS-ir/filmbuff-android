package retrofit.models

import com.google.gson.annotations.SerializedName

data class SerieBrief(
    val id: String,
    val slug: String,
    val title: String,
    val rating: Float,
    val visits: Int,
    val cover: String,
    val thumbnail: String,
    @SerializedName("is_vip")
    val isVip: Boolean,
)

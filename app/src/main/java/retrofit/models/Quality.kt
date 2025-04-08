package retrofit.models

import com.google.gson.annotations.SerializedName

data class Quality(
    val id: String,
    val slug: String,
    val title : String,
    @SerializedName("is_vip")
    val isVip: Boolean,
)

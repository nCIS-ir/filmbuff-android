package retrofit.models

import com.google.gson.annotations.SerializedName

data class BaseQuality(
    val id: String,
    val slug: String,
    val title : String,
    @SerializedName(value = "is_vip")
    val isVip: Boolean,
)

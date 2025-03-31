package retrofit.models

import com.google.gson.annotations.SerializedName

data class BaseQuality(
    val id: String,
    val slug: String,
    @SerializedName(value = "is_vip")
    val isVip: Boolean,
    val title : String,
)

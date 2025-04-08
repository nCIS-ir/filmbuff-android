package retrofit.models

import com.google.gson.annotations.SerializedName

data class Purchase(
    val id: String,
    @SerializedName("pack_id")
    val packId: String,
    val coins: Int,
    val authority: String,
    val reference: String,
    @SerializedName("paid_at")
    val paidAt: String?,
)

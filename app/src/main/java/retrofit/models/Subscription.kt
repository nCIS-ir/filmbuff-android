package retrofit.models

import com.google.gson.annotations.SerializedName

data class Subscription(
    val id: String,
    @SerializedName("plan_id")
    val planId: String,
    val price: Int,
    @SerializedName("started_at")
    val startedAt: String,
    @SerializedName("ended_at")
    val endedAt: String,
    val duration: Int,
)

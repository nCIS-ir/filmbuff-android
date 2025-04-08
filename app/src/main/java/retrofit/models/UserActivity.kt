package retrofit.models

import com.google.gson.annotations.SerializedName

data class UserActivity(
    val id: String,
    @SerializedName("activity_id")
    val activityId: String,
    val coins: Int,
    @SerializedName("done_at")
    val doneAt: String,
)

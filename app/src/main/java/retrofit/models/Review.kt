package retrofit.models

import com.google.gson.annotations.SerializedName

data class Review(
    val id: String,
    val username: String,
    val score: Int,
    val content: String,
    @SerializedName("submitted_at")
    val submittedAt: String,
    @SerializedName("is_active")
    val isActive: Boolean,
)
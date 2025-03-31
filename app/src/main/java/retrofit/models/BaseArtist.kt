package retrofit.models

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

data class BaseArtist(
    val id: String,
    @SerializedName(value = "role_id" )
    val roleId: String,
    @SerializedName(value = "country_id" )
    val countryId: String,
    val slug: String,
    @SerializedName(value = "born_at" )
    val bornAt: String,
    @SerializedName(value = "died_at" )
    val diedAt: String,
    @SerializedName(value = "full_name" )
    val fullName: String,
    val biograghy: String,
)

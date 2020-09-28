package us.martypants.hca.models

import com.google.gson.annotations.SerializedName

data class SoData(
    @SerializedName("has_more")
    val has_more: Boolean,

    @SerializedName("items")
    val items: List<Item>,

    @SerializedName("quota_maz")
    val quota_max: Int,

    @SerializedName("quota_remaining")
    val quota_remaining: Int
)
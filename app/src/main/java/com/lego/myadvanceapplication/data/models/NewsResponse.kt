package com.lego.myadvanceapplication.data.models

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("data") val listingData: ListingData
)

data class ListingData(
    @SerializedName("children") val children: List<ChildrenResponse>,
    @SerializedName("after") val after: String?,
    @SerializedName("before") val before: String?
)

data class ChildrenResponse(
    @SerializedName("data") val post: Post
)
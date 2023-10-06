package com.moneyminions.data.model.home.request

import com.google.gson.annotations.SerializedName
data class AnnouncementEditRequest(
    @SerializedName("noticeId")
    var noticeId: Int,
    @SerializedName("content")
    var content: String,
    @SerializedName("link")
    var link: String,
    @SerializedName("title")
    var title: String
)
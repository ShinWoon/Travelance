package com.moneyminions.data.model.traveldone.response


import com.google.gson.annotations.SerializedName

data class NoticeAllResponseDto(
    @SerializedName("content")
    val content: String,
    @SerializedName("link")
    val link: String,
    @SerializedName("noticeId")
    val noticeId: Int,
    @SerializedName("title")
    val title: String
)
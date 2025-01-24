package com.knowmerce.core.data.model.remote

import com.google.gson.annotations.SerializedName

/**
 * 동영상 응답 결과
 * @property title String 동영상 제목
 * @property url String 동영상 링크
 * @property datetime String 동영상 등록일, ISO 8601 ISO 8601 (YYYY)-(MM)-(DD)T(hh):(mm):(ss).000+(tz)
 * @property playTime Int 동영상 재생시간, 초 단위
 * @property thumbnail String 동영상 미리보기 URL
 * @property author String 동영상 업로더
 * @constructor
 */
data class VideoClipDocumentResponse(
    @SerializedName("title") val title: String,
    @SerializedName("url") val url: String,
    @SerializedName("datetime") val datetime: String,
    @SerializedName("play_time") val playTime: Int,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("author") val author: String,
)

package com.knowmerce.core.data.model.remote

import com.google.gson.annotations.SerializedName

/**
 * 이미지 응답 결과
 * @property collection String 컬렉션
 * @property thumbnailUrl String 미리보기 이미지 URL
 * @property imageUrl String 이미지 URL
 * @property width Int 이미지의 가로 길이
 * @property height Int 이미지의 세로 길이
 * @property displaySiteName String 출처
 * @property docUrl String 문서 URL
 * @property datetime String 문서 작성시간, ISO 8601 (YYYY)-(MM)-(DD)T(hh):(mm):(ss).000+(tz)
 * @constructor
 */
data class ImageDocumentResponse(
    @SerializedName("collection") val collection: String,
    @SerializedName("thumbnail_url") val thumbnailUrl: String,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("width") val width: Int,
    @SerializedName("height") val height: Int,
    @SerializedName("display_sitename") val displaySiteName: String,
    @SerializedName("doc_url") val docUrl: String,
    @SerializedName("datetime") val datetime: String,
)

package com.knowmerce.core.data.model.remote

import com.google.gson.annotations.SerializedName

/**
 * 응답 관련 정보
 * @property totalCount Int 검색된 문서 수
 * @property pageableCount Int [totalCount] 중 노출 가능 문서 수
 * @property isEnd Boolean 현재 페이지가 마지막 페이지인지 여부, 값이 false면 page를 증가시켜 다음 페이지를 요청할 수 있음
 * @constructor
 */
data class MetaResponse(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("pageable_count") val pageableCount: Int,
    @SerializedName("is_end") val isEnd: Boolean,
)

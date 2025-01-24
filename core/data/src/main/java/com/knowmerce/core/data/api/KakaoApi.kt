package com.knowmerce.core.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface KakaoApi {
    @GET("v2/search/image")
    suspend fun searchImage(
        @Query("query") query: String,
        @Query("sort") sort: String = "accuracy",
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): ImageSearchResponse
}
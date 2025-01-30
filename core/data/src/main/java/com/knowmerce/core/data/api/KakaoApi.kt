package com.knowmerce.core.data.api

import com.knowmerce.core.data.model.remote.ImageSearchResponse
import com.knowmerce.core.data.model.remote.VideoClipSearchResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KakaoApi {
    @GET("v2/search/image")
    suspend fun searchImage(
        @Query("query") query: String,
        @Query("sort") sort: String = "accuracy",
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Header("Authorization") apiKey: String,
    ): ImageSearchResponse

    @GET("v2/search/vclip")
    suspend fun searchVideoClip(
        @Query("query") query: String,
        @Query("sort") sort: String = "accuracy",
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Header("Authorization") apiKey: String,
    ): VideoClipSearchResponse
}
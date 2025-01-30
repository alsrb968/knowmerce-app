package com.knowmerce.core.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object KakaoClient {
    private const val BASE_URL = "https://dapi.kakao.com"
    private var _retrofit: Retrofit? = null

    val retrofit: Retrofit =
        _retrofit ?: synchronized(this) {
            _retrofit ?: Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().also {
                    _retrofit = it
                }
        }
}
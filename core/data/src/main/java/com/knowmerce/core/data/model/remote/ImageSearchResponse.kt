package com.knowmerce.core.data.model.remote

import com.google.gson.annotations.SerializedName

data class ImageSearchResponse(
    @SerializedName("meta") val meta: MetaResponse,
    @SerializedName("documents") val documents: List<ImageDocumentResponse>
)

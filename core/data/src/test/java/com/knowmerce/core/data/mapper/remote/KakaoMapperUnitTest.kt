package com.knowmerce.core.data.mapper.remote

import com.knowmerce.core.data.model.remote.*
import org.junit.Assert
import org.junit.Test

class KakaoMapperUnitTest {
    @Test
    fun `image search response to domain`() {
        val meta = MetaResponse(
            totalCount = 100,
            pageableCount = 10,
            isEnd = true
        )
        val document = ImageDocumentResponse(
            collection = "collection",
            thumbnailUrl = "thumbnailUrl",
            imageUrl = "imageUrl",
            width = 320,
            height = 240,
            displaySiteName = "displaySiteName",
            docUrl = "docUrl",
            datetime = "datetime"
        )

        val response = ImageSearchResponse(
            meta = meta,
            documents = List(10) { document }
        )

        val imageSearch = response.toImageSearch()
        Assert.assertEquals(meta.toMeta(), imageSearch.meta)
        Assert.assertEquals(response.documents.map { it.toImageDocument() }, imageSearch.documents)
    }

    @Test
    fun `video clip search response to domain`() {
        val meta = MetaResponse(
            totalCount = 100,
            pageableCount = 10,
            isEnd = true
        )
        val document = VideoClipDocumentResponse(
            title = "title",
            url = "url",
            datetime = "datetime",
            playTime = 100,
            thumbnail = "thumbnail",
            author = "author"
        )

        val response = VideoClipSearchResponse(
            meta = meta,
            documents = List(10) { document }
        )

        val videoClipSearch = response.toVideoClipSearch()
        Assert.assertEquals(meta.toMeta(), videoClipSearch.meta)
        Assert.assertEquals(response.documents.map { it.toVideoClipDocument() }, videoClipSearch.documents)
    }
}
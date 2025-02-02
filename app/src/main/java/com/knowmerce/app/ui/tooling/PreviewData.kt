package com.knowmerce.app.ui.tooling

import com.knowmerce.core.domain.model.SearchContent

val PreviewSearchContent = SearchContent(
    thumbnailUrl = "https://search3.kakaocdn.net/argon/138x78_80_pr/2Tu0mVdvuD1",
    title = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
    datetime = "2025-01-27T10:40:26.000+09:00",
    playTime = 70,
)

val PreviewSearchContents = List(10) { PreviewSearchContent }
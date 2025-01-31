package com.knowmerce.core.domain.extensions

import com.knowmerce.core.domain.extensions.ConvertExtensions.toDateTime
import com.knowmerce.core.domain.extensions.ConvertExtensions.toPlayTime
import org.junit.Assert
import org.junit.Test

class ConvertExtensionsUnitTest {
    @Test
    fun `test toDateTime`() {
        val datetime = "2025-01-27T10:40:26.000+09:00"
        val result = datetime.toDateTime()
        Assert.assertEquals("2025.1.27", result)
    }

    @Test
    fun `test toPlayTime`() {
        val playTime = 100
        val result = playTime.toPlayTime()
        Assert.assertEquals("1:40", result)
    }
}
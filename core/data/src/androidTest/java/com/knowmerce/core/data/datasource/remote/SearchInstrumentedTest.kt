package com.knowmerce.core.data.datasource.remote

import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

//@HiltAndroidTest
//@RunWith(AndroidJUnit4::class)
//class SearchInstrumentedTest {
//
//    @get:Rule
//    val hiltRule = HiltAndroidRule(this)
//
//    @Inject
//    lateinit var dataSource: KakaoDataSource
//
//    @Before
//    fun setUp() {
//        hiltRule.inject()
//    }
//
//    @Test
//    fun testSearchImage() = runBlocking {
//        val response = dataSource.searchImage(
//            query = "카리나",
//            page = 1,
//            size = 10,
//        )
//
//        Assert.assertTrue(response.documents.isNotEmpty())
//    }
//}
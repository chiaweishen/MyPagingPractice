package com.scw.mypagingpractice

import com.scw.mypagingpractice.di.module.apiModule
import com.scw.mypagingpractice.network.api.GithubApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.get

@OptIn(ExperimentalCoroutinesApi::class)
class GithubApiTest : KoinTest {
    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(apiModule)
    }

    @Test
    fun `test search kotlin repos`() = runTest {
        val itemPerPage = 30
        val res = get<GithubApi>().reposByName("kotlin", 1, itemPerPage)
        Assert.assertTrue(res.items.size == itemPerPage)
    }
}
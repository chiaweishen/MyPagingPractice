package com.scw.mypagingpractice

import com.scw.mypagingpractice.di.module.*
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
        modules(dbModule, mediatorModule, apiModule, repoModule, viewModelModule, dataSourceModule)
    }

    @Test
    fun `test search kotlin repos`() = runTest {
        val itemPerPage = 30
        val res = get<GithubApi>().kotlinRepos(1, itemPerPage)
        Assert.assertTrue(res.items.size == itemPerPage)
    }
}
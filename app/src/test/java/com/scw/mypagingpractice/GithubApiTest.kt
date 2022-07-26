package com.scw.mypagingpractice

import com.scw.mypagingpractice.di.module.apiModule
import com.scw.mypagingpractice.network.api.GithubApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
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
        get<GithubApi>()
            .searchKotlinRepos(1, 10)
            .catch { e ->
                Assert.fail(e.message)
            }
            .collect { res ->
                Assert.assertTrue(res.items.size == 10)
            }
    }
}
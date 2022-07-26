package com.scw.mypagingpractice.network.api

import com.scw.mypagingpractice.network.api.entity.RepoSearchResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {
    @GET("search/repositories?sort=stars&q=kotlin")
    fun searchKotlinRepos(
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): Flow<RepoSearchResponse>
}
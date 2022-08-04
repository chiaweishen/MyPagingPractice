package com.scw.mypagingpractice.network.api

import com.scw.mypagingpractice.model.entity.RepoSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {
    @GET("search/repositories?sort=stars&order=desc&q=kotlin")
    suspend fun kotlinRepos(
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): RepoSearchResponse
}
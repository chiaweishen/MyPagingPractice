package com.scw.mypagingpractice.network.api

import com.scw.mypagingpractice.network.api.entity.RepoSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {
    @GET("search/repositories?sort=stars")
    suspend fun reposByName(
        @Query("q") q: String,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): RepoSearchResponse
}
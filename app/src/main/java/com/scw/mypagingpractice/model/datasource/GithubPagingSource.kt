package com.scw.mypagingpractice.model.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.scw.mypagingpractice.network.api.GithubApi
import com.scw.mypagingpractice.network.api.entity.Repo
import timber.log.Timber

class GithubPagingSource(private val githubApi: GithubApi) : PagingSource<Int, Repo>() {

    companion object {
        const val NETWORK_PAGE_SIZE = 30
        const val QUERY = "kotlin"
    }

    override fun getRefreshKey(state: PagingState<Int, Repo>): Int? {
        Timber.i("refresh anchorPosition: ${state.anchorPosition}")
        state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            Timber.i("refresh position: $position, prevKey: ${page?.prevKey}, nextKey: ${page?.nextKey}")
        }
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> {
        return try {
            val position = params.key ?: 1
            val repos = githubApi.reposByName(QUERY, position, NETWORK_PAGE_SIZE).items
            val preKey = if (position == 1) null else position - 1
            val nextKey = position + 1
            Timber.i("load position: $position, preKey: $preKey, nextKey: $nextKey")
            LoadResult.Page(repos, preKey, nextKey)
        } catch (e: Exception) {
            Timber.e("load error: ${e.message}")
            LoadResult.Error(e)
        }
    }
}
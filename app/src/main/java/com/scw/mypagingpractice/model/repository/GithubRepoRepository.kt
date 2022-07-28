package com.scw.mypagingpractice.model.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.scw.mypagingpractice.model.datasource.GithubPagingSource
import com.scw.mypagingpractice.model.datasource.GithubPagingSource.Companion.NETWORK_PAGE_SIZE
import com.scw.mypagingpractice.network.api.entity.Repo
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class GithubRepoRepository : KoinComponent {

    fun getRepos(): Flow<PagingData<Repo>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
                prefetchDistance = NETWORK_PAGE_SIZE * 2
            ),
            pagingSourceFactory = { get<GithubPagingSource>() },
            initialKey = 1
        ).flow
    }
}
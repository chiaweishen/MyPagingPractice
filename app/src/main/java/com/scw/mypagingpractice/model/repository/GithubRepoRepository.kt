package com.scw.mypagingpractice.model.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.scw.mypagingpractice.db.RepoDatabase
import com.scw.mypagingpractice.model.entity.Repo
import com.scw.mypagingpractice.model.remote.mediator.RepoRemoteMediator
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent

@ExperimentalPagingApi
class GithubRepoRepository(
    private val database: RepoDatabase,
    private val remoteMediator: RepoRemoteMediator
) : KoinComponent {

    fun getRepos(query: String): Flow<PagingData<Repo>> {
        return Pager(
            config = PagingConfig(
                pageSize = 30,
                enablePlaceholders = false,
                prefetchDistance = 30 * 2
            ),
            pagingSourceFactory = { database.repoDao().pagingSource(query) },
            remoteMediator = remoteMediator,
            initialKey = 1
        ).flow
    }
}
package com.scw.mypagingpractice.model.remote.mediator

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.scw.mypagingpractice.db.RepoDatabase
import com.scw.mypagingpractice.model.entity.RemoteKeys
import com.scw.mypagingpractice.model.entity.Repo
import com.scw.mypagingpractice.network.api.GithubApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class RepoRemoteMediator(
    private val database: RepoDatabase,
    private val githubApi: GithubApi
) : RemoteMediator<Int, Repo>() {
    private val repoDao = database.repoDao()
    private val remoteKeysDao = database.remoteKeysDao()

    override suspend fun initialize(): InitializeAction {
//        val cacheTimeout = TimeUnit.MILLISECONDS.convert(10, TimeUnit.SECONDS)
//        return if (System.currentTimeMillis() - database.lastUpdated() >= cacheTimeout) {
//            InitializeAction.SKIP_INITIAL_REFRESH
//        } else {
//            InitializeAction.LAUNCH_INITIAL_REFRESH
//        }

        return InitializeAction.SKIP_INITIAL_REFRESH
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Repo>): MediatorResult {
        Timber.d("loadType: $loadType")
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = state.anchorPosition?.let { position ->
                    state.closestItemToPosition(position)?.id?.let { repoId ->
                        remoteKeysDao.getRemoteKeys(repoId)
                    }
                }
                remoteKeys?.nextKey?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                val remoteKeys = state.pages.firstOrNull {
                    it.data.isNotEmpty()
                }?.data?.firstOrNull()?.let { repo ->
                    remoteKeysDao.getRemoteKeys(repo.id)
                }
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = state.pages.lastOrNull() {
                    it.data.isNotEmpty()
                }?.data?.lastOrNull()?.let { repo ->
                    remoteKeysDao.getRemoteKeys(repo.id)
                }
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                nextKey
            }
        }

        Timber.d("page: $page")

        return try {
            val response = githubApi.kotlinRepos(page, state.config.pageSize)
            val repos = response.items
            val endOfPaginationReached = repos.isEmpty()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    remoteKeysDao.clearAll()
                    repoDao.clearAll()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = repos.map {
                    RemoteKeys(repoId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                remoteKeysDao.insertAll(keys)
                repoDao.insertAll(repos)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: IOException) {
            Timber.e(Log.getStackTraceString(e))
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            Timber.e(Log.getStackTraceString(e))
            MediatorResult.Error(e)
        }
    }
}
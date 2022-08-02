@file:OptIn(ExperimentalPagingApi::class)

package com.scw.mypagingpractice.di.module

import androidx.paging.ExperimentalPagingApi
import com.scw.mypagingpractice.db.RepoDatabase
import com.scw.mypagingpractice.model.remote.mediator.RepoRemoteMediator
import com.scw.mypagingpractice.model.repository.GithubRepoRepository
import com.scw.mypagingpractice.network.ApiService
import com.scw.mypagingpractice.ui.main.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dbModule = module {
    single { RepoDatabase.getInstance(androidContext()) }
}

val apiModule = module {
    single { ApiService() }
    single { get<ApiService>().getGithubApi() }
}

val mediatorModule = module {
    single { RepoRemoteMediator(get(), get()) }
}

val repoModule = module {
    single { GithubRepoRepository(get(), get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}


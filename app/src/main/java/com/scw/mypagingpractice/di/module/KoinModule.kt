package com.scw.mypagingpractice.di.module

import com.scw.mypagingpractice.model.datasource.GithubPagingSource
import com.scw.mypagingpractice.model.repository.GithubRepoRepository
import com.scw.mypagingpractice.network.ApiService
import com.scw.mypagingpractice.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val apiModule = module {
    single { ApiService() }
    single { get<ApiService>().getGithubApi() }
    factory { GithubPagingSource(get()) }
    single { GithubRepoRepository() }
    viewModel { MainViewModel(get()) }
}

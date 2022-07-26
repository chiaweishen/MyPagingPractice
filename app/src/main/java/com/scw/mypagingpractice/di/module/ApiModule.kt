package com.scw.mypagingpractice.di.module

import com.scw.mypagingpractice.network.ApiClient
import org.koin.dsl.module

val apiModule = module {
    single { ApiClient() }
    single { get<ApiClient>().getGithubApi() }
}

package com.scw.mypagingpractice.network

import com.scw.mypagingpractice.network.adapter.FlowCallAdapterFactory
import com.scw.mypagingpractice.network.api.GithubApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

class ApiClient {

    companion object {
        private const val BASE_URL = "https://api.github.com/"
    }

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor { Timber.tag("OKHttp").d(it) })
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(FlowCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getGithubApi(): GithubApi {
        return retrofit.create(GithubApi::class.java)
    }
}
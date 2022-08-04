package com.scw.mypagingpractice.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.scw.mypagingpractice.model.repository.GithubRepoRepository
import com.scw.mypagingpractice.model.entity.Repo
import kotlinx.coroutines.flow.Flow

@ExperimentalPagingApi
class MainViewModel(private val githubRepoRepository: GithubRepoRepository) : ViewModel() {

    fun getRepos(): Flow<PagingData<Repo>> {
        return githubRepoRepository.getRepos()
//        return githubRepoRepository.getRepos().cachedIn(viewModelScope)
    }

}
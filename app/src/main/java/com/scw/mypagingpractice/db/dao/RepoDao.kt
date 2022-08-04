package com.scw.mypagingpractice.db.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.scw.mypagingpractice.model.entity.Repo

@Dao
interface RepoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<Repo>)

    @Query("SELECT * FROM repo_table ORDER BY stars DESC")
    fun pagingSource(): PagingSource<Int, Repo>

    @Query("DELETE FROM repo_table")
    suspend fun clearAll()
}
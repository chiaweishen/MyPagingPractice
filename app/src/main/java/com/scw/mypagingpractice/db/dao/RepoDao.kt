package com.scw.mypagingpractice.db.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.scw.mypagingpractice.model.entity.Repo

@Dao
interface RepoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(repos: List<Repo>)

    @Query("SELECT * FROM repo_table WHERE name LIKE :query")
    fun pagingSource(query: String): PagingSource<Int, Repo>

    @Query("DELETE FROM repo_table")
    fun clearAll()
}
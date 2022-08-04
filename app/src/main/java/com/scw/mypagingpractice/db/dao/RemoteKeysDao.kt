package com.scw.mypagingpractice.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.scw.mypagingpractice.model.entity.RemoteKeys

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeys>)

    @Query("SELECT * FROM remote_keys WHERE repoId = :repoId")
    suspend fun getRemoteKeys(repoId: Long): RemoteKeys?

    @Query("DELETE FROM remote_keys")
    suspend fun clearAll()
}
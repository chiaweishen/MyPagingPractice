package com.scw.mypagingpractice.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.scw.mypagingpractice.db.dao.RemoteKeysDao
import com.scw.mypagingpractice.db.dao.RepoDao
import com.scw.mypagingpractice.model.entity.RemoteKeys
import com.scw.mypagingpractice.model.entity.Repo
import timber.log.Timber
import java.util.concurrent.Executors

@Database(
    entities = [Repo::class, RemoteKeys::class],
    version = 1
)
abstract class RepoDatabase : RoomDatabase() {
    abstract fun repoDao(): RepoDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {
        fun getInstance(context: Context): RepoDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                RepoDatabase::class.java,
                "repo_db"
            )
                .setQueryCallback({ sqlQuery, bindArgs ->
                    Timber.i("SQL: $sqlQuery, Args: $bindArgs")
                }, Executors.newSingleThreadExecutor())
                .build()
        }
    }
}
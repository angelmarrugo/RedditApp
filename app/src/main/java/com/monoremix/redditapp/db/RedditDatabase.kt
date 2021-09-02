package com.monoremix.redditapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.monoremix.myredditapp.model.Post
import com.monoremix.redditapp.model.RemoteKeys

@Database(
    entities = [Post::class, RemoteKeys::class],
    version = 9,
    exportSchema = false
)
abstract class RedditDatabase: RoomDatabase() {

    abstract fun postsDao(): PostDao
    abstract fun remoteKeysDao(): RemoteKeyDao

    companion object {
        @Volatile
        private var INSTANCE: RedditDatabase? = null

        fun getInstance(context: Context): RedditDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
            RedditDatabase::class.java, "reddit.db")
                .fallbackToDestructiveMigration()
                .build()
    }
}
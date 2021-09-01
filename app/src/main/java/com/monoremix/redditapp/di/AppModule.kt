package com.monoremix.redditapp.di

import android.content.Context
import com.monoremix.redditapp.api.RedditService
import com.monoremix.redditapp.db.PostDao
import com.monoremix.redditapp.db.RedditDatabase
import com.monoremix.redditapp.db.RemoteKeyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val baseUrl = "https://www.reddit.com/"

        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BASIC

        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideApiClient(retrofit: Retrofit): RedditService {
        return retrofit.create(RedditService::class.java)
    }

    @Singleton
    @Provides
    fun providePostDao(database: RedditDatabase): PostDao {
        return database.postsDao()
    }

    @Singleton
    @Provides
    fun provideRemoteKeysDao(database: RedditDatabase): RemoteKeyDao {
        return database.remoteKeysDao()
    }

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext app: Context): RedditDatabase {
        return RedditDatabase.getInstance(app)
    }

}
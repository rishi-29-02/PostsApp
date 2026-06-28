package com.rm.postapp.di

import android.content.Context
import androidx.room.Room
import com.rm.postapp.data.database.AppDatabase
import com.rm.postapp.data.database.PostDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {

        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "post_db"
        ).build()
    }

    @Provides
    fun provideUserDao(
        db: AppDatabase
    ): PostDao {

        return db.postDao()
    }

}
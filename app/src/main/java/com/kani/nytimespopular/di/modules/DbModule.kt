package com.kani.nytimespopular.modules

import android.app.Application
import androidx.annotation.NonNull
import androidx.room.Room
import com.kani.nytimespopular.data.local.AppDatabase
import com.kani.nytimespopular.data.local.ArticleDao
import com.kani.nytimespopular.utils.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class DbModule {

    private val DATABASE_NAME = "articles-data"

    @ApplicationScope
    @Provides
    internal fun provideDatabase(@NonNull application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration().build()
    }

    @ApplicationScope
    @Provides
    internal fun provideArticleDao(appDatabase: AppDatabase): ArticleDao {
        return appDatabase.articleDao()
    }

}
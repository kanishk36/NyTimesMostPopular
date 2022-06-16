package com.kani.nytimespopular.di.modules

import com.kani.nytimespopular.data.local.ArticleDao
import com.kani.nytimespopular.data.remote.ArticleApiService
import com.kani.nytimespopular.data.repository.ArticleDataSource
import com.kani.nytimespopular.data.repository.ArticleRepository
import com.kani.nytimespopular.utils.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @ApplicationScope
    @Provides
    fun provideRemoteDataSource(
        articleDao: ArticleDao, articleService: ArticleApiService): ArticleDataSource {
        return ArticleRepository(articleDao, articleService)
    }
}
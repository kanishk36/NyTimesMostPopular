package com.kani.nytimespopular

import com.kani.nytimespopular.data.local.ArticleDao
import com.kani.nytimespopular.data.remote.ArticleApiService
import com.kani.nytimespopular.data.repository.ArticleDataSource
import com.kani.nytimespopular.utils.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class TestAppModule {

    @ApplicationScope
    @Provides
    fun provideRemoteDataSource(
        articleDao: ArticleDao, articleService: ArticleApiService
    ): ArticleDataSource {
        return TestDataSource(articleDao, articleService)
    }
}
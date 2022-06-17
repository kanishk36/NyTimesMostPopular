package com.kani.nytimespopular

import com.kani.nytimespopular.data.local.ArticleDao
import com.kani.nytimespopular.data.remote.ArticleApiService
import com.kani.nytimespopular.data.repository.ArticleDataSource
import com.kani.nytimespopular.utils.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class AndroidTestAppModule {

    @ApplicationScope
    @Provides
    fun provideRemoteDataSource(
        articleDao: ArticleDao, articleService: ArticleApiService
    ): ArticleDataSource {
        return AndroidTestDataSource(articleDao, articleService)
    }
}
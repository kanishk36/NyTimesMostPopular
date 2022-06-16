package com.kani.nytimespopular.di.modules

import com.kani.nytimespopular.data.remote.ArticleApiService
import com.kani.nytimespopular.modules.NetworkModule
import com.kani.nytimespopular.utils.ApplicationScope
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module(includes = [NetworkModule::class])
class ArticleServiceModule {

    @ApplicationScope
    @Provides
    fun provideArticleService(retrofit: Retrofit): ArticleApiService = retrofit.create(
        ArticleApiService::class.java)
}
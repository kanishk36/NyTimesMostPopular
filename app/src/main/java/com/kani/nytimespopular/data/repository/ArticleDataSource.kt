package com.kani.nytimespopular.data.repository

import com.kani.nytimespopular.data.local.ArticleEntity
import com.kani.nytimespopular.data.local.ArticleWithImage
import com.kani.nytimespopular.data.remote.ArticleApiResponse
import com.kani.nytimespopular.utils.Response
import io.reactivex.Observable

interface ArticleDataSource {

    fun getArticleList(period: Int): Observable<Response<ArticleApiResponse>>

    fun loadFromDb(): Response<List<ArticleWithImage>>

    fun saveArticlesIntoDb(items: List<ArticleEntity>)
}
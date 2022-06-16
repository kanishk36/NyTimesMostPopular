package com.kani.nytimespopular.data.repository

import com.kani.nytimespopular.data.local.ArticleDao
import com.kani.nytimespopular.data.local.ArticleEntity
import com.kani.nytimespopular.data.local.ArticleImageEntity
import com.kani.nytimespopular.data.local.ArticleWithImage
import com.kani.nytimespopular.data.remote.ArticleApiResponse
import com.kani.nytimespopular.data.remote.ArticleApiService
import com.kani.nytimespopular.utils.Response
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

import javax.inject.Inject


class ArticleRepository(
    private val articleDao: ArticleDao,
    private val articleService: ArticleApiService) {

    fun getArticleList(period: Int): Observable<Response<ArticleApiResponse>> {
        return articleService.fetchArticlesByPeriod(period)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map<Response<ArticleApiResponse>> {
                if(it.results.isNotEmpty()) {
                    saveArticlesIntoDb(it.results)
                }
                Response.Result(it)
            }
            .onErrorReturn { throwable -> Response.Error(throwable) }
    }

    fun loadFromDb(): Response<List<ArticleWithImage>> = runBlocking {
        val data = async(Dispatchers.IO) {
            articleDao.getArticleList()
        }

        val movieEntities = data.await()
        return@runBlocking if(movieEntities.isEmpty()) {
            Response.Error(Throwable("DB Error !!!"))
        } else {
            Response.Result(movieEntities)
        }
    }

    private fun saveArticlesIntoDb(items: List<ArticleEntity>) = runBlocking {

        launch(Dispatchers.IO) {
            articleDao.deleteAllArticles()
            articleDao.deleteAllImages()
            articleDao.insertArticles(items)

            for (articleEntity in items) {
                if(articleEntity.media.isNotEmpty() && articleEntity.media[0].mediaMetaData.isNotEmpty()) {
                    articleEntity.mapArticleIdToImageEntity()
                    articleDao.insertArticlesImages(articleEntity.media[0].mediaMetaData)
                }
            }
        }
    }

}
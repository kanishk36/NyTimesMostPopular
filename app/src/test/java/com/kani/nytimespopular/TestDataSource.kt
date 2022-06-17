package com.kani.nytimespopular

import com.google.gson.GsonBuilder
import com.kani.nytimespopular.data.local.ArticleDao
import com.kani.nytimespopular.data.local.ArticleEntity
import com.kani.nytimespopular.data.local.ArticleImageEntity
import com.kani.nytimespopular.data.local.ArticleWithImage
import com.kani.nytimespopular.data.remote.ArticleApiResponse
import com.kani.nytimespopular.data.remote.ArticleApiService
import com.kani.nytimespopular.data.repository.ArticleDataSource
import com.kani.nytimespopular.utils.Response
import io.reactivex.Observable
import kotlinx.coroutines.runBlocking
import java.io.File

class TestDataSource (
private val articleDao: ArticleDao,
private val articleService: ArticleApiService): ArticleDataSource {

    private val filePath = "../app/src/test/res/"
    private val fileName = "articles.json"

    companion object {
        var savedList = ArrayList<ArticleWithImage>()
    }

    override fun getArticleList(period: Int): Observable<Response<ArticleApiResponse>> {
        val file = File(filePath.plus(fileName))

        val jsonResponse = String(file.readBytes())
        val articleApiResponse = GsonBuilder().create().fromJson(jsonResponse, ArticleApiResponse::class.java)

        return Observable.just(Response.Result(articleApiResponse))
    }

    override fun loadFromDb(): Response<List<ArticleWithImage>> {
        return Response.Result(savedList)
    }

    override fun saveArticlesIntoDb(items: List<ArticleEntity>): Unit = runBlocking {
        savedList.addAll(getMergedArticleList(items))
    }

    private fun getMergedArticleList(items: List<ArticleEntity>): List<ArticleWithImage> {
        val list = ArrayList<ArticleWithImage>()
        for (articleEntity in items) {
            val images = ArrayList<ArticleImageEntity>()
            if(articleEntity.media.isNotEmpty() && articleEntity.media[0].mediaMetaData.isNotEmpty()) {
                images.addAll(articleEntity.media[0].mediaMetaData)
            }
            list.add(ArticleWithImage(articleEntity, images))
        }

        return list
    }

}
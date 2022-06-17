package com.kani.nytimespopular

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.gson.GsonBuilder
import com.kani.nytimespopular.data.local.ArticleDao
import com.kani.nytimespopular.data.local.ArticleEntity
import com.kani.nytimespopular.data.local.ArticleWithImage
import com.kani.nytimespopular.data.remote.ArticleApiResponse
import com.kani.nytimespopular.data.remote.ArticleApiService
import com.kani.nytimespopular.data.repository.ArticleDataSource
import com.kani.nytimespopular.utils.Response
import io.reactivex.Observable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.runner.RunWith
import java.io.IOException
import java.io.InputStreamReader

class AndroidTestDataSource (
    private val articleDao: ArticleDao,
    private val articleService: ArticleApiService
): ArticleDataSource {

    private val fileName = "articles.json"

    override fun getArticleList(period: Int): Observable<Response<ArticleApiResponse>> {
        val jsonResponse = readStringFromFile()
        val articleApiResponse = GsonBuilder().create().fromJson(jsonResponse, ArticleApiResponse::class.java)

        return Observable.just(Response.Result(articleApiResponse))
    }

    override fun loadFromDb(): Response<List<ArticleWithImage>> = runBlocking {
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

    override fun saveArticlesIntoDb(items: List<ArticleEntity>): Unit = runBlocking {
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

    private fun readStringFromFile(): String {
        try {
            val inputStream = InstrumentationRegistry.getInstrumentation().targetContext
                .applicationContext.assets.open(fileName)
            val builder = StringBuilder()
            val reader = InputStreamReader(inputStream, "UTF-8")
            reader.readLines().forEach {
                builder.append(it)
            }
            return builder.toString()
        } catch (e: IOException) {
            throw e
        }
    }

}
package com.kani.nytimespopular

import com.kani.nytimespopular.data.local.ArticleEntity
import com.kani.nytimespopular.data.local.ArticleImageEntity
import com.kani.nytimespopular.data.local.ArticleWithImage
import com.kani.nytimespopular.data.repository.ArticleDataSource
import com.kani.nytimespopular.utils.Response
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import javax.inject.Inject

class FetchArticlesDataTest {

    @Inject
    lateinit var remoteDataSource: ArticleDataSource

    companion object {
        var remoteList = ArrayList<ArticleWithImage>()
    }

    @Before
    fun setup() {

        val application = TestBaseApplication()
        val component: TestAppComponent = DaggerTestAppComponent.factory().create(application)
        component.inject(application)
        component.inject(this)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun fetchArticlesResponse_testMethodCall() = runTest {
        val articleList = async { remoteDataSource.getArticleList(7) }
        articleList.await().subscribe { response ->
            assert(response is Response.Result)

            when(response) {
                is Response.Result -> {
                    remoteDataSource.saveArticlesIntoDb(response.data.results)
                    remoteList.addAll(getMergedArticleList(response.data.results))
                } else -> {}
            }
        }

    }

    @ExperimentalCoroutinesApi
    @Test
    fun loadFromDatabase() = runTest {
        val list: List<ArticleWithImage>
        when(val dbResponse = remoteDataSource.loadFromDb()) {
            is Response.Result -> {
                list = dbResponse.data
                assert(list == remoteList)
            } else -> {}
        }
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
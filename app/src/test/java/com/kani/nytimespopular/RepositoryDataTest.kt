package com.kani.nytimespopular

import com.kani.nytimespopular.data.repository.ArticleDataSource
import com.kani.nytimespopular.utils.Response
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import javax.inject.Inject

@RunWith(MockitoJUnitRunner::class)
class RepositoryDataTest {

    @Inject
    lateinit var remoteDataSource: ArticleDataSource

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
        }

    }
}
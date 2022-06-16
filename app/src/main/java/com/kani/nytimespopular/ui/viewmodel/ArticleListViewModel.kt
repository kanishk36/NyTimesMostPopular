package com.kani.nytimespopular.ui.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kani.nytimespopular.data.local.ArticleDao
import com.kani.nytimespopular.data.local.ArticleEntity
import com.kani.nytimespopular.data.local.ArticleImageEntity
import com.kani.nytimespopular.data.local.ArticleWithImage
import com.kani.nytimespopular.data.remote.ArticleApiService
import com.kani.nytimespopular.data.repository.ArticleDataSource
import com.kani.nytimespopular.utils.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

@SuppressLint("CheckResult")
class ArticleListViewModel @Inject constructor(
    articleDao: ArticleDao, articleApiService: ArticleApiService
): ViewModel() {

    @Inject
    internal lateinit var articleRepository: ArticleDataSource

    private val _articleListData = MutableLiveData<List<ArticleWithImage>>()
    val articleList: LiveData<List<ArticleWithImage>> = _articleListData

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _article = MutableLiveData<ArticleEntity>()
    val article: LiveData<ArticleEntity> = _article

    fun setArticle(article: ArticleEntity) {
        _article.value = article
    }

    fun getArticles(period: Int) = viewModelScope.launch {

        val articleListResponse = async(Dispatchers.IO) {
            articleRepository.getArticleList(period)
        }

        articleListResponse.await().subscribe { apiResponse ->
            when(apiResponse) {
                is Response.Result -> {
                    _articleListData.postValue(getArticleListFromResponse(apiResponse.data.results))
                }

                is Response.Error -> {
                    when(val dbResponse = articleRepository.loadFromDb()) {
                        is Response.Result -> {
                            if(dbResponse.data.isEmpty()) {
                                setError(apiResponse.throwable)
                            } else {
                                _articleListData.postValue(dbResponse.data!!)
                            }
                        }
                        is Response.Error -> {}
                    }
                    setError(apiResponse.throwable)
                }
            }
        }
    }

    fun fetchArticlesFromDbInitially() = viewModelScope.launch {
        when(val dbResponse = articleRepository.loadFromDb()) {
            is Response.Result -> {
                _articleListData.postValue(dbResponse.data!!)
            }
            else -> {}
        }

    }

    private fun getArticleListFromResponse(articleEntityList: List<ArticleEntity>): List<ArticleWithImage> {
        val articlesList= ArrayList<ArticleWithImage>()

        for (articleEntity in articleEntityList) {
            val images: List<ArticleImageEntity>
            if(articleEntity.media.isNotEmpty() && articleEntity.media.get(0).mediaMetaData.isNotEmpty()) {
                images = articleEntity.media.get(0).mediaMetaData as ArrayList<ArticleImageEntity>
            } else {
                images = ArrayList()
            }
            articlesList.add(ArticleWithImage(articleEntity, images))
        }

        return articlesList
    }

    private fun setError(throwable: Throwable) {
        when(throwable) {
            is UnknownHostException -> _errorMessage.postValue("Check your internet connection")
            is SocketTimeoutException -> _errorMessage.postValue("Slow internet connection")
            is IOException -> _errorMessage.postValue("Something went wrong !!!")
            else -> {
                _errorMessage.postValue("Something went wrong !!!")

                throwable.localizedMessage?.let {
                    Log.e(ArticleListViewModel::class.java.name, it)
                }
            }
        }
    }

}
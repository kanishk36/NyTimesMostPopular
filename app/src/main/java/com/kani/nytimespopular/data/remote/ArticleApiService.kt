package com.kani.nytimespopular.data.remote

import com.kani.nytimespopular.utils.CommonUtils
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArticleApiService {

    @GET("all-sections/{period}.json")
    fun fetchArticlesByPeriod(@Path("period") period: Int, @Query("api-key") apiKey: String = CommonUtils.API_KEY): Observable<ArticleApiResponse>
}
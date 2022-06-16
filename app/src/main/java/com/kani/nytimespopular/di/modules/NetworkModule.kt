package com.kani.nytimespopular.modules

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kani.nytimespopular.di.modules.ContextModule
import com.kani.nytimespopular.utils.ApplicationScope
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module(includes = [ContextModule::class])
class NetworkModule {

    private val maxSize = (5*1024*1024).toLong() //5 MB
    private val connectionTimeout = 30L
    private val readTimeout = 30L
    private val baseUrl = "http://api.nytimes.com/svc/mostpopular/v2/mostviewed/"

    @ApplicationScope
    @Provides
    internal fun provideGson(): Gson = GsonBuilder().create()

    @ApplicationScope
    @Provides
    internal fun provideCache(context: Context): Cache = Cache(context.cacheDir, maxSize)

    @ApplicationScope
    @Provides
    internal fun provideInterceptor(): Interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @ApplicationScope
    @Provides
    internal fun provideHttpClient(interceptor: Interceptor, cache: Cache): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor)
        httpClient.cache(cache)
        httpClient.connectTimeout(connectionTimeout, TimeUnit.SECONDS)
        httpClient.readTimeout(readTimeout, TimeUnit.SECONDS)

        return httpClient.build()
    }

    @ApplicationScope
    @Provides
    internal fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()
    }

}
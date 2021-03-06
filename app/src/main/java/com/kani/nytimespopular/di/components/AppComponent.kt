package com.kani.nytimespopular.di.components

import android.app.Application
import android.content.Context
import com.kani.nytimespopular.NyPopularApplication
import com.kani.nytimespopular.di.modules.AppModule
import com.kani.nytimespopular.di.modules.ArticleServiceModule
import com.kani.nytimespopular.di.modules.ItemDetailHostActivityModule
import com.kani.nytimespopular.modules.DbModule
import com.kani.nytimespopular.modules.ViewModelModule
import com.kani.nytimespopular.utils.ApplicationScope
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule

@ApplicationScope
@Component(modules = [
    DbModule::class,
    ViewModelModule::class,
    ItemDetailHostActivityModule::class,
    ArticleServiceModule::class,
    AndroidInjectionModule::class,
    AndroidSupportInjectionModule::class,
    AppModule::class])
interface AppComponent: AndroidInjector<DaggerApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }

    fun inject(app: NyPopularApplication)
}
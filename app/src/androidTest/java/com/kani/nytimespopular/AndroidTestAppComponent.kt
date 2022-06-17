package com.kani.nytimespopular

import android.app.Application
import com.kani.nytimespopular.di.components.AppComponent
import com.kani.nytimespopular.di.modules.ArticleServiceModule
import com.kani.nytimespopular.di.modules.ItemDetailHostActivityModule
import com.kani.nytimespopular.modules.DbModule
import com.kani.nytimespopular.modules.ViewModelModule
import com.kani.nytimespopular.utils.ApplicationScope
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule

@ApplicationScope
@Component(modules = [
    DbModule::class,
    ViewModelModule::class,
    ItemDetailHostActivityModule::class,
    ArticleServiceModule::class,
    AndroidInjectionModule::class,
    AndroidSupportInjectionModule::class,
    AndroidTestAppModule::class
])
interface AndroidTestAppComponent: AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AndroidTestAppComponent
    }

    fun inject(app: AndroidTestApplication)

}
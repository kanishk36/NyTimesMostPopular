package com.kani.nytimespopular.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kani.nytimespopular.ui.viewmodel.ArticleListViewModel
import com.kani.nytimespopular.utils.ViewModelFactory
import com.kani.nytimespopular.utils.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    /*
     * This method basically says
     * inject this object into a Map using the @IntoMap annotation,
     * with the  MovieListViewModel.class as key,
     * and a Provider that will build a MovieListViewModel
     * object.
     *
     * */

    @Binds
    @IntoMap
    @ViewModelKey(ArticleListViewModel::class)
    protected abstract fun articleListViewModel(articlesListViewModel: ArticleListViewModel): ViewModel
}
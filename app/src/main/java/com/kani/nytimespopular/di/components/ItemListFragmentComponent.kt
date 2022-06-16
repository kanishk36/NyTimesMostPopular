package com.kani.nytimespopular.di.components

import com.kani.nytimespopular.ui.fragment.ItemListFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent
internal interface ItemListFragmentComponent: AndroidInjector<ItemListFragment> {

    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<ItemListFragment>
}
package com.kani.nytimespopular.di.components

import com.kani.nytimespopular.ui.fragment.ItemDetailFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent
internal interface ItemDetailFragmentComponent: AndroidInjector<ItemDetailFragment> {

    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<ItemDetailFragment>
}
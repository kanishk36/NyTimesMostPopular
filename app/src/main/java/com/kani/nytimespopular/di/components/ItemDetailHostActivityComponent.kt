package com.kani.nytimespopular.di.components

import com.kani.nytimespopular.di.modules.ItemDetailFragmentModule
import com.kani.nytimespopular.di.modules.ItemListFragmentModule
import com.kani.nytimespopular.ui.activity.ItemDetailHostActivity
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent(
    modules = [ItemListFragmentModule::class, ItemDetailFragmentModule::class]
)
interface ItemDetailHostActivityComponent: AndroidInjector<ItemDetailHostActivity> {

    @Subcomponent.Factory
    interface Factory: AndroidInjector.Factory<ItemDetailHostActivity>
}
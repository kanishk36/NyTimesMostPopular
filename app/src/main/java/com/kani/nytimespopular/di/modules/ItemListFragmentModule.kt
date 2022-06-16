package com.kani.nytimespopular.di.modules

import com.kani.nytimespopular.di.components.ItemListFragmentComponent
import com.kani.nytimespopular.ui.fragment.ItemListFragment
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module(
    subcomponents = [ItemListFragmentComponent::class]
)
internal abstract class ItemListFragmentModule {

    @Binds
    @IntoMap
    @ClassKey(ItemListFragment::class)
    internal abstract fun bindItemListFragmentInjectorFactory(
        factory: ItemListFragmentComponent.Factory): AndroidInjector.Factory<*>
}
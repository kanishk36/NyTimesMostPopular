package com.kani.nytimespopular.di.modules

import com.kani.nytimespopular.di.components.ItemDetailFragmentComponent
import com.kani.nytimespopular.ui.fragment.ItemDetailFragment
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module(
    subcomponents = [ItemDetailFragmentComponent::class]
)
internal abstract class ItemDetailFragmentModule {

    @Binds
    @IntoMap
    @ClassKey(ItemDetailFragment::class)
    internal abstract fun bindItemDetailFragmentInjectorFactory(
        factory: ItemDetailFragmentComponent.Factory): AndroidInjector.Factory<*>
}
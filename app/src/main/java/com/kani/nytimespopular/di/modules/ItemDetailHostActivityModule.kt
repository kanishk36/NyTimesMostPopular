package com.kani.nytimespopular.di.modules

import com.kani.nytimespopular.di.components.ItemDetailHostActivityComponent
import com.kani.nytimespopular.ui.activity.ItemDetailHostActivity
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module(subcomponents = [ItemDetailHostActivityComponent::class])
abstract class ItemDetailHostActivityModule {

    @Binds
    @IntoMap
    @ClassKey(ItemDetailHostActivity::class)
    internal abstract fun bindItemDetailHostActivityFactory(
        factory: ItemDetailHostActivityComponent.Factory
    ): AndroidInjector.Factory<*>
}
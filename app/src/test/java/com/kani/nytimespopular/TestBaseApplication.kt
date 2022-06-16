package com.kani.nytimespopular

import com.kani.nytimespopular.di.components.AppComponent

class TestBaseApplication: NyPopularApplication() {

    override fun initializeDaggerComponent(): AppComponent {
        val component: TestAppComponent = DaggerTestAppComponent.factory().create(this)
        component.inject(this)
        return component
    }
}
package com.kani.nytimespopular

import com.kani.nytimespopular.di.components.AppComponent

class AndroidTestApplication: NyPopularApplication() {

    override fun initializeDaggerComponent(): AppComponent {
        val component: AndroidTestAppComponent = DaggerAndroidTestAppComponent.factory().create(this)
        component.inject(this)
        return component
    }

}
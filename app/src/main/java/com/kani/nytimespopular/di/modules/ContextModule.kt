package com.kani.nytimespopular.di.modules

import android.content.Context
import dagger.Module
import javax.inject.Inject

@Module
class ContextModule {

    @Inject
    lateinit var context: Context

}
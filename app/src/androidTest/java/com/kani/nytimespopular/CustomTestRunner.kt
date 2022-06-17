package com.kani.nytimespopular

import android.app.Application
import android.content.Context

class CustomTestRunner: androidx.test.runner.AndroidJUnitRunner() {

    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, AndroidTestApplication::class.java.name, context)
    }


}
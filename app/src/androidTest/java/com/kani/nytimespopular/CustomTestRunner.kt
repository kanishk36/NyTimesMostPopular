package com.kani.nytimespopular

import android.app.Application
import android.content.Context

class CustomTestRunner: androidx.test.runner.AndroidJUnitRunner() {

    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, TestBaseApplication::class.java.name, context)
    }


}
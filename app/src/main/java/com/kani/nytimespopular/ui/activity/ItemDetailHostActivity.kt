package com.kani.nytimespopular.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.kani.nytimespopular.R
import com.kani.nytimespopular.databinding.ActivityItemDetailBinding
import com.kani.nytimespopular.ui.viewmodel.ArticleListViewModel
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class ItemDetailHostActivity : AppCompatActivity(), HasAndroidInjector {

    private lateinit var appBarConfiguration: AppBarConfiguration

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Any>

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidInjection.inject(this)
        initialiseViewModel()

        val binding = ActivityItemDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_item_detail) as NavHostFragment
        val navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_item_detail)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return fragmentInjector
    }

    private fun initialiseViewModel() {
        ViewModelProvider(this, viewModelFactory)[ArticleListViewModel::class.java]
    }
}
package com.kani.nytimespopular.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kani.nytimespopular.databinding.FragmentItemDetailBinding
import com.kani.nytimespopular.ui.viewmodel.ArticleListViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a [ItemListFragment]
 * in two-pane mode (on larger screen devices) or self-contained
 * on handsets.
 */
class ItemDetailFragment : Fragment() {

    private var _binding: FragmentItemDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var articleViewModel: ArticleListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
        initialiseViewModel()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setHasOptionsMenu(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentItemDetailBinding.inflate(inflater, container, false)

        _binding!!.articleDetails.settings.useWideViewPort = true
        _binding!!.articleDetails.settings.loadWithOverviewMode = true

        var url = ""

        articleViewModel.article.value?.let {
            it.url?.let {
                url = it
            }
        }

        _binding!!.articleDetails.loadUrl(url)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initialiseViewModel() {
        articleViewModel = ViewModelProvider(activity!!, viewModelFactory)[ArticleListViewModel::class.java]

        articleViewModel.article.observe(this, {
            it.url?.let {
                binding.articleDetails.loadUrl(it)
            }
        })
    }
}
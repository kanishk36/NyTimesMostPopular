package com.kani.nytimespopular.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.kani.nytimespopular.R
import com.kani.nytimespopular.data.local.ArticleEntity
import com.kani.nytimespopular.data.local.ArticleWithImage
import com.kani.nytimespopular.databinding.FragmentItemListBinding
import com.kani.nytimespopular.ui.adapter.ArticleListAdapter
import com.kani.nytimespopular.ui.viewmodel.ArticleListViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/**
 * A Fragment representing a list of Pings. This fragment
 * has different presentations for handset and larger screen devices. On
 * handsets, the fragment presents a list of items, which when touched,
 * lead to a {@link ItemDetailFragment} representing
 * item details. On larger screens, the Navigation controller presents the list of items and
 * item details side-by-side using two vertical panes.
 */

class ItemListFragment : Fragment(), ArticleListAdapter.OnItemClickListener {

    private var _binding: FragmentItemListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var articleViewModel: ArticleListViewModel
    lateinit var articleListAdapter: ArticleListAdapter

    private var articlesList = ArrayList<ArticleWithImage>()

    private var mPeriod = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidSupportInjection.inject(this)
        initialiseViewModel()
        fetchArticlesInitially()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentItemListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setPullToRefresh()
        initialiseView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.article_list_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(binding.progressLayout.visibility == View.GONE &&
            !binding.swipeRefresh.isRefreshing) {

            when(item.title) {
                getString(R.string.menu_item_period1) -> {
                    mPeriod = 1
                }
                getString(R.string.menu_item_period7) -> {
                    mPeriod = 7
                }
                getString(R.string.menu_item_period30) -> {
                    mPeriod = 30
                }
            }
            binding.progressLayout.visibility = View.VISIBLE
            fetchArticles()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initialiseView() {
        if(articlesList.isNotEmpty()) {
            updateViewOnDataFetch()
        }
    }

    private fun initialiseViewModel() {
        articleViewModel = ViewModelProvider(activity!!, viewModelFactory)[ArticleListViewModel::class.java]
        articleViewModel.articleList.observe(this, {
            updateArticleList(it)
        })

        articleViewModel.errorMessage.observe(this, {
            Toast.makeText(activity!!, it, Toast.LENGTH_LONG).show()

            if(articlesList.isEmpty()) {
                updateViewOnNoDataFetched()
            }
        })
    }

    private fun fetchArticlesInitially() {
        articleViewModel.fetchArticlesFromDbInitially()
        fetchArticles()
    }

    private fun fetchArticles() = articleViewModel.getArticles(mPeriod)

    private fun setupRecyclerView() {
        articleListAdapter = ArticleListAdapter(
            this, articlesList
        )
        binding.itemList.adapter = articleListAdapter
    }

    private fun setPullToRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            articleViewModel.getArticles(mPeriod)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateArticleList(articles: List<ArticleWithImage>) {

        articleListAdapter.setUpdatedList(articles)
        articleListAdapter.notifyDataSetChanged()
        articlesList.clear()
        articlesList.addAll(articles)

        setSelectedArticle(articles[0].article)

        updateViewOnDataFetch()
    }

    override fun selectedArticle(article: ArticleEntity) {
        setSelectedArticle(article)

        val itemDetailFragmentContainer: View? = binding.root.findViewById(R.id.item_detail_nav_container)

        if (itemDetailFragmentContainer != null) {
            itemDetailFragmentContainer.findNavController()
                .navigate(R.id.fragment_item_detail, Bundle())
        } else {
            binding.root.findNavController().navigate(R.id.show_item_detail, Bundle())
        }
    }

    private fun setSelectedArticle(article: ArticleEntity) = articleViewModel.setArticle(article)

    private fun updateViewOnNoDataFetched() {
        if(binding.progressLayout.visibility == View.VISIBLE)
            binding.progressLayout.visibility = View.GONE

        binding.txtNoData.visibility = View.VISIBLE

        binding.swipeRefresh.isRefreshing = false
    }

    private fun updateViewOnDataFetch() {
        binding.swipeRefresh.isRefreshing = false

        if(binding.progressLayout.visibility == View.VISIBLE)
            binding.progressLayout.visibility = View.GONE

        if(binding.txtNoData.visibility == View.VISIBLE)
            binding.txtNoData.visibility = View.GONE
    }
}
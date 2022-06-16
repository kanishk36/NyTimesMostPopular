package com.kani.nytimespopular.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kani.nytimespopular.R
import com.kani.nytimespopular.data.local.ArticleEntity
import com.kani.nytimespopular.data.local.ArticleWithImage
import com.kani.nytimespopular.databinding.ItemListContentBinding

class ArticleListAdapter(
    @NonNull private val onClickListener: OnItemClickListener,
    private val articleList: ArrayList<ArticleWithImage>) :
    RecyclerView.Adapter<ArticleListAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun selectedArticle(article: ArticleEntity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: ItemListContentBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.item_list_content, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articleList[position].article
        holder.viewBinding.article = article
        holder.viewBinding.imgArticle.setImageResource(R.drawable.ic_image_download)

        if(article.media.isNotEmpty() && article.media[0].mediaMetaData.isNotEmpty()) {
            val image = article.media[0].mediaMetaData[0]
            Glide.with(holder.itemView)
                .load(image.url)
                .placeholder(R.drawable.ic_image_download)
                .error(R.drawable.ic_broken_image)
                .into(holder.viewBinding.imgArticle)
        }

        holder.itemView.setOnClickListener {
            onClickListener.selectedArticle(article)
        }
    }

    override fun getItemCount(): Int = articleList.size

    inner class ViewHolder(binding: ItemListContentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val viewBinding: ItemListContentBinding = binding
    }

    fun setUpdatedList(list: List<ArticleWithImage>) {
        articleList.clear()
        articleList.addAll(list)
    }

}
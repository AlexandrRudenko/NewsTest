package com.rudenko.alexandr.vjettest.ui.articles

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.rudenko.alexandr.vjettest.R
import com.rudenko.alexandr.vjettest.data.Article
import com.rudenko.alexandr.vjettest.utils.DateFormatter
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_article.*
import kotlinx.android.synthetic.main.item_loading.*

class ArticlesAdapter(
    private val onItemClick: (item: Article) -> Unit,
    private val onShareClick: (item: Article) -> Unit,
    private val onSaveClick: (item: Article) -> Unit,
    private val onRetryClick: () -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var retryPageLoad: Boolean = false
    private var errorMsg: String = ""

    companion object {
        private const val ITEM_ARTICLE = 0
        private const val ITEM_LOADING = 1
    }

    private var items: MutableList<Article> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ITEM_ARTICLE -> {
                val viewNews = inflater.inflate(R.layout.item_article, parent, false)
                ArticleViewHolder(viewNews)
            }
            else -> {
                val viewLoading = inflater.inflate(R.layout.item_loading, parent, false)
                LoadingViewHolder(viewLoading)
            }
        }
    }

    override fun getItemCount(): Int = items.size + 1

    override fun getItemViewType(position: Int): Int = if (position == items.size) ITEM_LOADING else ITEM_ARTICLE

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        when (viewType) {
            ITEM_ARTICLE -> {
                val item = items[position]
                (viewHolder as ArticleViewHolder).bind(item)
            }
            ITEM_LOADING -> (viewHolder as LoadingViewHolder).showRetry(retryPageLoad, errorMsg)
        }
    }

    fun clear() {
        val count = itemCount
        items.clear()
        notifyItemRangeRemoved(0, count)
    }

    fun add(items: List<Article>) {
        val oldCount = itemCount
        this.items.addAll(items)
        notifyItemRangeInserted(oldCount-1, items.size)
    }

    fun showLoading() {
        retryPageLoad = false
        notifyItemChanged(itemCount-1)
    }

    fun showError(msg: String) {
        retryPageLoad = true
        errorMsg = msg
        notifyItemChanged(itemCount-1)
    }

    internal inner class ArticleViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(item: Article) {
            source.text = item.source.name
            time.text = DateFormatter.format(item.publishedAt)
            title.text = item.title

            if (item.urlToImage.isNullOrEmpty()) {
                image.visibility = GONE
            } else {
                Picasso.get().load(item.urlToImage).into(image)
                image.visibility = VISIBLE
            }

            if (item.description.isNullOrEmpty()) {
                description.visibility = GONE
            } else {
                description.text = item.description
                description.visibility = VISIBLE
            }

            description.text = item.description
            saveBtn.visibility = VISIBLE


            containerView.setOnClickListener { onItemClick(item) }
            shareBtn.setOnClickListener { onShareClick(item) }
            saveBtn.setOnClickListener { onSaveClick(item) }
        }
    }

    internal inner class LoadingViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer{

        init {
            loadmore_errorlayout.setOnClickListener { onRetryClick() }
        }

        fun showRetry(retry: Boolean, errorMsg: String?) {
            loadmore_progress.visibility = if (retry) View.GONE else View.VISIBLE
            loadmore_errorlayout.visibility = if (retry) View.VISIBLE else View.GONE
            loadmore_errortxt.text = errorMsg
        }
    }
}
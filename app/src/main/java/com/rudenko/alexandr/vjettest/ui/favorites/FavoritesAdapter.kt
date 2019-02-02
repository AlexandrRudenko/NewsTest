package com.rudenko.alexandr.vjettest.ui.favorites

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

class FavoritesAdapter(
    private val onItemClick: (item: Article) -> Unit,
    private val onShareClick: (item: Article) -> Unit,
    private val onRemoveClick: (item: Article) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<Article> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewNews = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return ArticleViewHolder(viewNews)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        (viewHolder as ArticleViewHolder).bind(items[position])
    }


    fun setData(items: List<Article>) {
        this.items = items
        notifyDataSetChanged()
    }

    inner class ArticleViewHolder(
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

            removeBtn.visibility = VISIBLE

            containerView.setOnClickListener { onItemClick(item) }
            shareBtn.setOnClickListener { onShareClick(item) }
            removeBtn.setOnClickListener { onRemoveClick(item) }
        }
    }

}
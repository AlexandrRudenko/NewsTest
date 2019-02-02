package com.rudenko.alexandr.vjettest.ui.favorites

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rudenko.alexandr.vjettest.App
import com.rudenko.alexandr.vjettest.R
import com.rudenko.alexandr.vjettest.data.Article
import com.rudenko.alexandr.vjettest.ui.OnFragmentSelectedListener
import kotlinx.android.synthetic.main.fragment_articles.*
import kotlinx.android.synthetic.main.layout_empty.*
import javax.inject.Inject

class FavoritesFragment : Fragment(), FavoritesContract.View, OnFragmentSelectedListener {

    companion object {
        fun newInstance() = FavoritesFragment()
    }

    @Inject
    protected lateinit var presenter: FavoritesContract.Presenter
    private lateinit var adapter: FavoritesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.instance.component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_favorites, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = FavoritesAdapter(
            onItemClick = { presenter.onArticleClick(it) },
            onShareClick = { presenter.onShareClick(it) },
            onRemoveClick = { presenter.onRemoveClick(it) }
        )
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        presenter.bind(this, savedInstanceState)
    }

    override fun onFragmentSelected() {
        presenter.onFragmentSelected()
    }

    override fun setData(articles: List<Article>) {
        adapter.setData(articles)
        recyclerView.visibility = View.VISIBLE
        loading.visibility = View.GONE
        empty_layout.visibility = View.GONE

    }

    override fun showFullscreenLoading() {
        loading.visibility = View.VISIBLE
        empty_layout.visibility = View.GONE
        recyclerView.visibility = View.GONE
    }

    override fun openArticleDetails(item: Article) {

    }

    override fun shareArticle(item: Article) {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, item.url)

        startActivity(Intent.createChooser(intent, getString(R.string.share_chooser_title)))
    }

    override fun showEmpty() {
        empty_layout.visibility = View.VISIBLE
        loading.visibility = View.GONE
        recyclerView.visibility = View.GONE
    }
}
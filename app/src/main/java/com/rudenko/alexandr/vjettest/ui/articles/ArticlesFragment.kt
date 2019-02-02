package com.rudenko.alexandr.vjettest.ui.articles

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import com.rudenko.alexandr.vjettest.App
import com.rudenko.alexandr.vjettest.R
import com.rudenko.alexandr.vjettest.common.PaginationScrollListener
import com.rudenko.alexandr.vjettest.data.Article
import com.rudenko.alexandr.vjettest.data.sources.SearchParameters
import com.rudenko.alexandr.vjettest.ui.OnFragmentSelectedListener
import kotlinx.android.synthetic.main.fragment_articles.*
import kotlinx.android.synthetic.main.layout_empty.*
import kotlinx.android.synthetic.main.layout_error.*
import javax.inject.Inject

class ArticlesFragment : Fragment(), ArticlesContract.View, OnFragmentSelectedListener {

    companion object {
        const val SEARCH_DIALOG = 345

        fun newInstance() = ArticlesFragment()
    }

    @Inject
    protected lateinit var presenter: ArticlesContract.Presenter
    private lateinit var adapter: ArticlesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_articles, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        App.instance.component.inject(this)

        adapter = ArticlesAdapter(
            onItemClick = { presenter.onArticleClick(it) },
            onShareClick = { presenter.onShareClick(it) },
            onSaveClick = { presenter.onSaveClick(it) },
            onRetryClick = { presenter.onDownScrolled() }
        )
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        recyclerView.addOnScrollListener(PaginationScrollListener(
            layoutManager,
            {presenter.onDownScrolled()}
        ))

        searchButton.setOnClickListener { presenter.onClickSearch() }
        error_btn_retry.setOnClickListener { presenter.onClickRetry() }

        presenter.bind(this, savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        presenter.saveState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbind()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SEARCH_DIALOG && resultCode == RESULT_OK) {
            val searchParameters = data?.getParcelableExtra<SearchParameters>(SearchParametersDialogFragment.EXTRA_SEARCH_PARAMETERS)
            searchParameters?.let {
                presenter.onSearchParametersChanged(it)
            }

        }
    }

    override fun onFragmentSelected() {

    }

    override fun openSearchParametersDialog(searchParameters: SearchParameters) {
        val fragment = SearchParametersDialogFragment.newInstance(searchParameters)
        fragment.setTargetFragment(this, SEARCH_DIALOG)
        fragment.show(fragmentManager, "searchDialog")
    }

    override fun setData(articles: List<Article>) {
        adapter.clear()
        addData(articles)
    }

    override fun addData(articles: List<Article>) {
        adapter.add(articles)
        empty_layout.visibility = GONE
        loading.visibility = GONE
        error_layout.visibility = GONE
        recyclerView.visibility = VISIBLE
    }

    override fun showLoadingMore() {
        adapter.showLoading()
    }

    override fun showLoadingMoreError(msg: String) {
        adapter.showError(msg)
    }

    override fun showLoadingMoreError(resId: Int) {
        showLoadingMoreError(getString(resId))
    }

    override fun showEmpty() {
        empty_layout.visibility = VISIBLE
        loading.visibility = GONE
        error_layout.visibility = GONE
        recyclerView.visibility = GONE
    }

    override fun showFullscreenLoading() {
        loading.visibility = VISIBLE
        error_layout.visibility = GONE
        recyclerView.visibility = GONE
        empty_layout.visibility = GONE
    }

    override fun showError(msg: String) {
        error_txt_cause.text = msg
        error_layout.visibility = VISIBLE
        loading.visibility = GONE
        recyclerView.visibility = GONE
        empty_layout.visibility = GONE
    }

    override fun showError(resId: Int) {
        showError(getString(resId))
    }

    override fun showSearchButton() {
        searchButton.show()
    }

    override fun openArticleDetails(item: Article) {

    }

    override fun shareArticle(item: Article) {

    }
}
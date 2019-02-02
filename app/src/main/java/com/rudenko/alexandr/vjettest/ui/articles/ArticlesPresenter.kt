package com.rudenko.alexandr.vjettest.ui.articles

import android.os.Bundle
import com.rudenko.alexandr.vjettest.R
import com.rudenko.alexandr.vjettest.data.Article
import com.rudenko.alexandr.vjettest.data.sources.AppRepository
import com.rudenko.alexandr.vjettest.data.sources.SearchParameters
import com.rudenko.alexandr.vjettest.common.BasePresenterImpl
import com.rudenko.alexandr.vjettest.utils.NetworkUtils
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.rxkotlin.subscribeBy
import java.net.SocketTimeoutException
import javax.inject.Inject

class ArticlesPresenter @Inject constructor(
    private val repository: AppRepository
) : BasePresenterImpl<ArticlesContract.View>(), ArticlesContract.Presenter {

    companion object {
        private const val BUNDLE_SEARCH_PARAMETERS_KEY = "BUNDLE_SEARCH_PARAMETERS_KEY"
        private const val BUNDLE_ARTICLES_LIST_KEY = "BUNDLE_ARTICLES_LIST_KEY"
        private const val BUNDLE_PAGES_LOADED_KEY = "BUNDLE_PAGES_LOADED_KEY"
        private const val PAGE_SIZE = 10
    }

    private var disposable: Disposable = Disposables.empty()
    private var articles: MutableList<Article> = ArrayList()

    private var pagesLoaded: Int = 0
    private var isInit: Boolean = false
    private var isLoading: Boolean = false
    private lateinit var searchParameters: SearchParameters

    override fun bind(view: ArticlesContract.View, savedState: Bundle?) {
        super.bind(view, savedState)

        val savedSearchParameters: SearchParameters? = savedState?.getParcelable(BUNDLE_SEARCH_PARAMETERS_KEY)
        val savedArticles: MutableList<Article>? = savedState?.getParcelableArrayList(BUNDLE_ARTICLES_LIST_KEY)
        pagesLoaded = savedState?.getInt(BUNDLE_PAGES_LOADED_KEY) ?: 0

        if (savedSearchParameters == null) {
            init()
        } else {
            searchParameters = savedSearchParameters
            getView()?.showSearchButton()
            if (savedArticles == null) {
                loadData(true)
            } else {
                articles = savedArticles
                getView()?.setData(articles)
            }
        }
    }

    override fun saveState(state: Bundle) {
        state.putParcelable(BUNDLE_SEARCH_PARAMETERS_KEY, searchParameters)
        state.putParcelableArrayList(BUNDLE_ARTICLES_LIST_KEY, ArrayList(articles))
        state.putInt(BUNDLE_PAGES_LOADED_KEY, pagesLoaded)
    }

    override fun onDownScrolled() {
        loadData(false)
    }

    override fun onClickRetry() {
        if (isInit) {
            loadData(true)
        } else {
            init()
        }
    }

    override fun onClickSearch() {
        getView()?.openSearchParametersDialog(searchParameters.copy())
    }

    override fun onSearchParametersChanged(searchParameters: SearchParameters) {
        this.searchParameters = searchParameters
        loadData(true)
    }

    override fun onArticleClick(item: Article) {
        getView()?.openArticleDetails(item)
    }

    override fun onShareClick(item: Article) {
        getView()?.shareArticle(item)
    }

    override fun onSaveClick(item: Article) {
        repository.saveArticleToFavorites(item)
            .subscribe()
    }

    private fun init() {
        getView()?.showFullscreenLoading()

        disposable = repository.getSources()
            .flatMap{
                searchParameters = SearchParameters(it, null, null)
                getView()?.showSearchButton()
                repository.getTopHeadlines(0, PAGE_SIZE)
            }
            .subscribeBy(
                onSuccess = {
                    isInit = true
                    onDataLoaded(it, true)
                },
                onError = {
                    onLoadingError(it, true)
                }
            )
    }

    private fun loadData(force: Boolean) {
        if (isLoading) return
        isLoading = true

        if (force) {
            getView()?.showFullscreenLoading()
            articles.clear()
            pagesLoaded = 0
        } else {
            getView()?.showLoadingMore()
        }

        if (!disposable.isDisposed) disposable.dispose()

        if (searchParameters.sources.any {it.selected}) {
            loadArticles(force)
        } else {
            loadTopHeadlines(force)
        }

    }

    private fun loadArticles(force: Boolean) {
        disposable = repository.getArticles(searchParameters, pagesLoaded+1, PAGE_SIZE)
            .subscribeBy(
                onSuccess = {onDataLoaded(it, force)},
                onError = {onLoadingError(it, force)}
            )
    }

    private fun loadTopHeadlines(force: Boolean) {
        disposable = repository.getTopHeadlines(pagesLoaded+1, PAGE_SIZE)
            .subscribeBy(
                onSuccess = {onDataLoaded(it, force)},
                onError = {onLoadingError(it, force)}
            )
    }

    private fun onDataLoaded(items: List<Article>, force: Boolean) {
        isLoading = false
        pagesLoaded++

        articles.addAll(items)
        if (force) {
            if (items.isEmpty()) {
                getView()?.showEmpty()
            } else {
                getView()?.setData(items)
            }

        } else {
            getView()?.addData(items)
        }
    }

    private fun onLoadingError(throwable: Throwable, force: Boolean) {
        isLoading = false
        val msg = getMessageResIdForError(throwable)
        if (force) {
            getView()?.showError(msg)
        } else {
            getView()?.showLoadingMoreError(msg)
        }

    }

    private fun getMessageResIdForError(e: Throwable): Int {
        return if (!NetworkUtils.isOnline()) {
            R.string.error_msg_no_internet
        } else if (e is SocketTimeoutException) {
            R.string.error_msg_timeout
        } else {
            R.string.error_msg_unknown
        }
    }

    override fun unbind() {
        super.unbind()
        isLoading = false
        if (!disposable.isDisposed) disposable.dispose()
    }
}
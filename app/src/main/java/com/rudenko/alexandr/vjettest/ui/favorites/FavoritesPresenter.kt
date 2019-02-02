package com.rudenko.alexandr.vjettest.ui.favorites

import android.os.Bundle
import com.rudenko.alexandr.vjettest.common.BasePresenterImpl
import com.rudenko.alexandr.vjettest.data.Article
import com.rudenko.alexandr.vjettest.data.sources.AppRepository
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class FavoritesPresenter @Inject constructor(
    private val repository: AppRepository
) : BasePresenterImpl<FavoritesContract.View>(), FavoritesContract.Presenter {

    companion object {
        private const val BUNDLE_ARTICLES_LIST_KEY = "BUNDLE_ARTICLES_LIST_KEY"
    }

    private var disposable: Disposable = Disposables.empty()
    private var articles: List<Article> = ArrayList()

    override fun bind(view: FavoritesContract.View, savedState: Bundle?) {
        super.bind(view, savedState)

        val savedArticles: MutableList<Article>? = savedState?.getParcelableArrayList(BUNDLE_ARTICLES_LIST_KEY)
        if (savedArticles != null) {
            articles = savedArticles
            getView()?.setData(articles)
        } else {
            reloadData()
        }

    }

    override fun onFragmentSelected() {
        reloadData()
    }

    override fun saveState(state: Bundle) {
        state.putParcelableArrayList(BUNDLE_ARTICLES_LIST_KEY, ArrayList(articles))
    }

    override fun onArticleClick(item: Article) {
        getView()?.openArticleDetails(item)
    }

    override fun onShareClick(item: Article) {
        getView()?.shareArticle(item)
    }

    override fun onRemoveClick(item: Article) {
        repository.removeArticleFromFavorites(item)
            .subscribeBy {
                reloadData()
            }

    }

    override fun unbind() {
        super.unbind()
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
    }

    private fun reloadData() {
        if (!disposable.isDisposed) {
            disposable.dispose()
        }

        getView()?.showFullscreenLoading()
        disposable = repository.getFavorites()
            .subscribeBy {
                onDataLoaded(it)
            }
    }

    private fun onDataLoaded(items: List<Article>) {
        articles = items
        if (items.isEmpty()) {
            getView()?.showEmpty()
        } else {
            getView()?.setData(items)
        }
    }




}
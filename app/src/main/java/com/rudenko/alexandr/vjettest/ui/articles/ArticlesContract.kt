package com.rudenko.alexandr.vjettest.ui.articles

import android.support.annotation.StringRes
import com.rudenko.alexandr.vjettest.data.Article
import com.rudenko.alexandr.vjettest.data.sources.SearchParameters
import com.rudenko.alexandr.vjettest.common.BasePresenter
import com.rudenko.alexandr.vjettest.common.BaseView

interface ArticlesContract {

    interface View : BaseView {

        fun openSearchParametersDialog(searchParameters: SearchParameters)

        fun addData(articles: List<Article>)

        fun setData(articles: List<Article>)

        fun showFullscreenLoading()

        fun showError(msg: String)

        fun showError(@StringRes resId: Int)

        fun showSearchButton()

        fun showLoadingMore()

        fun showLoadingMoreError(msg: String)

        fun showLoadingMoreError(@StringRes resId: Int)

        fun openArticleDetails(item: Article)

        fun shareArticle(item: Article)

    }

    interface Presenter : BasePresenter<View> {

        fun onClickRetry()

        fun onDownScrolled()

        fun onClickSearch()

        fun onSearchParametersChanged(searchParameters: SearchParameters)

        fun onArticleClick(item: Article)

        fun onShareClick(item: Article)

        fun onSaveClick(item: Article)

    }
}
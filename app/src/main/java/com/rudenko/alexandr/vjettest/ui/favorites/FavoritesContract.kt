package com.rudenko.alexandr.vjettest.ui.favorites

import com.rudenko.alexandr.vjettest.common.BasePresenter
import com.rudenko.alexandr.vjettest.common.BaseView
import com.rudenko.alexandr.vjettest.data.Article

interface FavoritesContract {

    interface View : BaseView {

        fun setData(articles: List<Article>)

        fun showFullscreenLoading()

        fun openArticleDetails(item: Article)

        fun shareArticle(item: Article)
    }

    interface Presenter : BasePresenter<View> {

        fun onFragmentSelected()

        fun onArticleClick(item: Article)

        fun onShareClick(item: Article)

        fun onRemoveClick(item: Article)

    }
}
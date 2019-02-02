package com.rudenko.alexandr.vjettest.ui

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.util.SparseArray
import android.view.ViewGroup
import com.rudenko.alexandr.vjettest.R
import com.rudenko.alexandr.vjettest.ui.articles.ArticlesFragment
import com.rudenko.alexandr.vjettest.ui.favorites.FavoritesFragment

class MainPagerAdapter(
    fragmentManager: FragmentManager,
    private val context: Context
) : FragmentPagerAdapter(fragmentManager) {

    companion object {
        const val COUNT = 2
        const val TAB_POSITION_ARTICLES = 0
        const val TAB_POSITION_FAVORITES = 1
    }

    private val fragments: SparseArray<Fragment> = SparseArray()

    override fun getItem(position: Int): Fragment? =
        when (position) {
            TAB_POSITION_ARTICLES -> ArticlesFragment.newInstance()
            TAB_POSITION_FAVORITES -> FavoritesFragment.newInstance()
            else -> null
        }

    override fun getCount(): Int = COUNT

    override fun getPageTitle(position: Int): CharSequence? =
        when (position) {
            TAB_POSITION_ARTICLES -> context.getString(R.string.fragment_title_articles)
            TAB_POSITION_FAVORITES -> context.getString(R.string.fragment_title_favorites)
            else -> null
        }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val item = super.instantiateItem(container, position)
        fragments.put(position, item as Fragment)
        return item
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        fragments.remove(position)
        super.destroyItem(container, position, `object`)
    }

    fun getFragmentAtPosition(position: Int): Fragment? = fragments.get(position, null)

}
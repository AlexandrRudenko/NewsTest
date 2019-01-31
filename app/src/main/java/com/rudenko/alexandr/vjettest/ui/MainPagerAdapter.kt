package com.rudenko.alexandr.vjettest.ui

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.rudenko.alexandr.vjettest.R

class MainPagerAdapter(
    fragmentManager: FragmentManager,
    private val context: Context,
    private val articlesFragment: Fragment,
    private val favoritesFragment: Fragment
) : FragmentPagerAdapter(fragmentManager) {

    companion object {
        const val COUNT = 2
        const val TAB_POSITION_ARTICLES = 0
        const val TAB_POSITION_FAVORITES = 1
    }

    override fun getItem(position: Int): Fragment? =
        when (position) {
            TAB_POSITION_ARTICLES -> articlesFragment
            TAB_POSITION_FAVORITES -> favoritesFragment
            else -> null
        }

    override fun getCount(): Int = COUNT

    override fun getPageTitle(position: Int): CharSequence? =
        when (position) {
            TAB_POSITION_ARTICLES -> context.getString(R.string.fragment_title_articles)
            TAB_POSITION_FAVORITES -> context.getString(R.string.fragment_title_favorites)
            else -> null
        }

}
package com.rudenko.alexandr.vjettest.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import com.rudenko.alexandr.vjettest.R
import com.rudenko.alexandr.vjettest.ui.articles.ArticlesFragment
import com.rudenko.alexandr.vjettest.ui.favorites.FavoritesFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val adapter = MainPagerAdapter(
            supportFragmentManager,
            baseContext,
            ArticlesFragment.newInstance(),
            FavoritesFragment.newInstance()
        )
        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(position: Int) {

            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

            }

            override fun onPageSelected(position: Int) {
                (adapter.getItem(position) as OnFragmentSelectedListener).onFragmentSelected()
            }
        })

        tabLayout.setupWithViewPager(viewPager)
    }
}

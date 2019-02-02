package com.rudenko.alexandr.vjettest.ui

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.rudenko.alexandr.vjettest.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val adapter = MainPagerAdapter(
            supportFragmentManager,
            baseContext
        )
        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(position: Int) {

            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

            }

            override fun onPageSelected(position: Int) {
                adapter.getFragmentAtPosition(position)?.let {
                    (it as OnFragmentSelectedListener).onFragmentSelected()
                }

            }
        })

        tabLayout.setupWithViewPager(viewPager)
    }
}

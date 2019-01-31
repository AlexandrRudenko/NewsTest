package com.rudenko.alexandr.vjettest.ui.articles

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rudenko.alexandr.vjettest.R
import com.rudenko.alexandr.vjettest.ui.OnFragmentSelectedListener

class ArticlesFragment : Fragment(), OnFragmentSelectedListener {

    companion object {
        fun newInstance() = ArticlesFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_articles, container, false)

    override fun onFragmentSelected() {

    }
}
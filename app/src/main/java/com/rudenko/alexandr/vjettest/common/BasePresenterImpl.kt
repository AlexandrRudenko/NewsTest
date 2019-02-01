package com.rudenko.alexandr.vjettest.common

import android.os.Bundle

abstract class BasePresenterImpl<V : BaseView> :
    BasePresenter<V> {

    private var view: V? = null

    override fun bind(view: V, savedState: Bundle?) {
        this.view = view
    }

    override fun unbind() {
        view = null
    }

    override fun getView(): V? = view
}
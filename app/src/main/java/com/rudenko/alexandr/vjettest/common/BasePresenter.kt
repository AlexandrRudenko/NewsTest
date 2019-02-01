package com.rudenko.alexandr.vjettest.common

import android.os.Bundle

interface BasePresenter<V : BaseView> {

    fun bind(view: V, savedState: Bundle?)

    fun unbind()

    fun getView(): V?

    fun saveState(state: Bundle)

}
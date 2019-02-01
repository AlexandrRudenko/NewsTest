package com.rudenko.alexandr.vjettest.common

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

class PaginationScrollListener(
    private val layoutManager: LinearLayoutManager,
    private val onDownScrolled: () -> Unit
) : RecyclerView.OnScrollListener() {

    companion object {
        private const val OFFSET = 1
    }

    private var firedOnDown: Boolean = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (dy > 0) {
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
            val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()


            firedOnDown = if (visibleItemCount + firstVisibleItem >= totalItemCount - OFFSET
                && firstVisibleItem >= 0) {
                if (!firedOnDown) {
                    onDownScrolled()
                }
                true
            } else {
                false
            }
        }
    }
}
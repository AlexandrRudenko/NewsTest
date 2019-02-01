package com.rudenko.alexandr.vjettest.ui.articles

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import com.rudenko.alexandr.vjettest.R
import com.rudenko.alexandr.vjettest.data.Source
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_source.*

class SourcesAdapter(
    private val items: List<Source>
) : RecyclerView.Adapter<SourcesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, postion: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_source, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(items[position])
    }

    class ViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(item: Source) {
            source_name.text = item.name
            source_name.isChecked = item.selected
            source_name.setOnClickListener { view -> item.selected = (view as CheckBox).isChecked }
        }

    }
}
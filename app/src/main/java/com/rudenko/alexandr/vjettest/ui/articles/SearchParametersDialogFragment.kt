package com.rudenko.alexandr.vjettest.ui.articles

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.rudenko.alexandr.vjettest.R
import com.rudenko.alexandr.vjettest.data.sources.SearchParameters
import kotlinx.android.synthetic.main.dialog_search_parameters.*
import java.text.SimpleDateFormat
import java.util.*

class SearchParametersDialogFragment : DialogFragment() {

    companion object {
        const val EXTRA_SEARCH_PARAMETERS = "EXTRA_SEARCH_PARAMETERS"

        @SuppressLint("SimpleDateFormat")
        private val dateFormat = SimpleDateFormat("yyyy-MM-dd")

        fun newInstance(searchParameters: SearchParameters): SearchParametersDialogFragment {
            val args = Bundle()
            args.putParcelable(EXTRA_SEARCH_PARAMETERS, searchParameters)

            val fragment = SearchParametersDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var searchParameters: SearchParameters? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchParameters = arguments?.getParcelable(EXTRA_SEARCH_PARAMETERS)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.dialog_search_parameters, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val sortSelectedPosition = when (searchParameters?.sort) {
            SearchParameters.Sort.POPULARITY -> 1
            else -> 0
        }

        val sortAdapter = ArrayAdapter.createFromResource(
            context,
            R.array.sort_by_items,
            android.R.layout.simple_spinner_dropdown_item
        )
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sort.adapter = sortAdapter
        sort.setSelection(sortSelectedPosition)
        sort.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                searchParameters?.sort = when (position) {
                    0 -> SearchParameters.Sort.POPULARITY
                    else -> SearchParameters.Sort.PUBLISHED_AT
                }
            }
        }

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter =
                SourcesAdapter(searchParameters?.sources ?: ArrayList())

        date_from.setOnClickListener {
            val selected = searchParameters?.from ?: Calendar.getInstance()
            showDatePicker(selected, DatePickerDialog.OnDateSetListener { _, y, m, d ->
                searchParameters?.from = GregorianCalendar().apply { set(y, m, d) }
                updateDateButtons()
            })
        }

        date_to.setOnClickListener {
            val selected = searchParameters?.to ?: Calendar.getInstance()
            showDatePicker(selected, DatePickerDialog.OnDateSetListener { _, y, m, d ->
                searchParameters?.to = GregorianCalendar().apply { set(y, m, d) }
                updateDateButtons()
            })
        }
        updateDateButtons()

        cancel.setOnClickListener { dismiss() }
        reset.setOnClickListener { _ ->
            searchParameters?.apply {
                sources.map { it.selected = false }
                from = null
                to = null
                sort = SearchParameters.Sort.POPULARITY
            }
            sendSearchParameters()
            dismiss()
        }
        searchButton.setOnClickListener {_ ->
            searchParameters?.let {
                if (it.sources.any { item -> item.selected}) {
                    sendSearchParameters()
                    dismiss()
                } else {
                    showNeedSources()
                }
            }
        }
    }

    private fun sendSearchParameters() {
        val intent = Intent()
        intent.putExtra(EXTRA_SEARCH_PARAMETERS, searchParameters)
        targetFragment?.onActivityResult(targetRequestCode, RESULT_OK, intent)
    }

    private fun updateDateButtons() {
        date_to.text = searchParameters?.to?.let { dateFormat.format(it.time) } ?: getString(R.string.btn_date_to)
        date_from.text = searchParameters?.from?.let { dateFormat.format(it.time) } ?: getString(R.string.btn_date_from)
    }

    private fun showNeedSources() {
        Toast.makeText(context, R.string.need_select_source, Toast.LENGTH_SHORT).show()
    }

    private fun showDatePicker(calendar: Calendar, listener: DatePickerDialog.OnDateSetListener) {
        val dialog = DatePickerDialog(
            context,
            listener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        dialog.datePicker.maxDate = Date().time
        dialog.show()
    }


}
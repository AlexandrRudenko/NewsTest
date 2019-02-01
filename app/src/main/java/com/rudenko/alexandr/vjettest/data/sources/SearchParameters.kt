package com.rudenko.alexandr.vjettest.data.sources

import android.os.Parcelable
import com.rudenko.alexandr.vjettest.data.Source
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class SearchParameters(
    var sources: List<Source>,
    var from: Calendar?,
    var to: Calendar?,
    var sort: Sort = Sort.PUBLISHED_AT
) : Parcelable {

    enum class Sort {
        PUBLISHED_AT,
        POPULARITY
    }
}
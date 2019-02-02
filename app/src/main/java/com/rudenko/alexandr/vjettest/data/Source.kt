package com.rudenko.alexandr.vjettest.data

import android.arch.persistence.room.ColumnInfo
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Source(
    @SerializedName("id")
    @ColumnInfo(name = "source_id")
    val id: String,

    @SerializedName("name")
    @ColumnInfo(name = "source_name")
    val name: String,

    var selected: Boolean = false
) : Parcelable
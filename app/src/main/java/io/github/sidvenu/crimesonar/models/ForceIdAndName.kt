package io.github.sidvenu.crimesonar.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ForceIdAndName (
        var id: String = "",
        var name: String = ""
) : Parcelable

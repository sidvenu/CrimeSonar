package io.github.sidvenu.crimesonar.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Outcome(
        var category: String?,
        var date: String?
) : Parcelable

package io.github.sidvenu.crimesonar.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Location(
        var latitude: String?,
        var longitude: String?,
        var street: Street?
) : Parcelable {
    @Parcelize
    data class Street(
            var id: Int?,
            var name: String?
    ) : Parcelable
}

package io.github.sidvenu.crimesonar.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Crime(
        var category: String? = null,
        @SerializedName("location_type") var locationType: String? = null,
        var location: Location? = null,
        var context: String? = null,
        @SerializedName("outcome_status") var outcome: Outcome? = null,
        @SerializedName("persistent_id") var persistentId: String? = null,
        var id: String? = null,
        @SerializedName("location_subtype") var locationSubtype: String? = null,
        var month: String? = null
) : Parcelable {
    fun getLocationName(): String? {
        if (location != null) {
            val street = location!!.street
            if (street != null) {
                return street.name
            }
        }
        return null
    }
}

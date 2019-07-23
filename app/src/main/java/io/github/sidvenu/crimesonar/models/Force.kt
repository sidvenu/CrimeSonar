package io.github.sidvenu.crimesonar.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Force(
        var id: String = "",
        var name: String = "",
        var description: String = "",
        var url: String = "",
        var telephone: String = "",
        @SerializedName("engagement_methods") var engagementMethods: List<EngagementMethod> = ArrayList()
) : Parcelable {
    @Parcelize
    data class EngagementMethod(
            var url: String = "",
            var description: String = "",
            var title: String = ""
    ) : Parcelable
}

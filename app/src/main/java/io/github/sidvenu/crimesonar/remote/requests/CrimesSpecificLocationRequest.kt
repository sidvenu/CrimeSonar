package io.github.sidvenu.crimesonar.remote.requests

import com.google.gson.annotations.SerializedName

data class CrimesSpecificLocationRequest(
        @SerializedName("location_id") var locationId: String,
        var category: String? = null
)

package io.github.sidvenu.crimesonar.remote.requests

data class CrimesLatLongRequest(
        var lat: String,
        var long: String,
        var date: String? = null
)

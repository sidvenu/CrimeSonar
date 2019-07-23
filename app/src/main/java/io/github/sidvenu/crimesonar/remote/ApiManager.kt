package io.github.sidvenu.crimesonar.remote

import io.github.sidvenu.crimesonar.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiManager {
    val policeService: PoliceService

    companion object {
        private var apiManager: ApiManager? = null

        val instance: ApiManager
            get() {
                if (apiManager == null) {
                    apiManager = ApiManager()
                }
                return apiManager as ApiManager
            }
    }

    init {
        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        policeService = retrofit.create(PoliceService::class.java)
    }
}

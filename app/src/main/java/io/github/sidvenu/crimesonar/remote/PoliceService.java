package io.github.sidvenu.crimesonar.remote;

import java.util.List;

import io.github.sidvenu.crimesonar.models.Crime;
import io.github.sidvenu.crimesonar.models.Force;
import io.github.sidvenu.crimesonar.models.ForceIdAndName;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PoliceService {
    @GET("forces")
    Call<List<ForceIdAndName>> getForcesIdAndName();

    @GET("forces/{force}")
    Call<Force> getForce(@Path("force") String id);

    @GET("crimes-at-location")
    Call<List<Crime>> getCrimesAtLocation(@Query("lat") String lat, @Query("lng") String lng, @Query("date") String date);

    @GET("crimes-at-location")
    Call<List<Crime>> getCrimesAtLocation(@Query("location_id") String locationId, @Query("category") String category);

    @GET("crimes-street/all-crime")
    Call<List<Crime>> getStreetLevelCrimes(@Query("lat") String lat, @Query("lng") String lng, @Query("date") String date);
}

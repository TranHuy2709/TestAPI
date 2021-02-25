package com.example.testvideoview;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {


    @GET("data/2.5/forecast/daily")
    Call<WeatherModel> getWeatherData(
            @Query("id") String cityid,
            @Query("appid") String token);
}

package com.example.testvideoview;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JSONApi {


    @GET("employees")
    Call<List<Employees>> getEmployees();


}

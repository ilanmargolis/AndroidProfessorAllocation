package com.example.retrofit.service;

import com.example.retrofit.model.Allocation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AllocationService {

    @POST("allocation")
    Call<Allocation> create(@Body Allocation allocation);

    @PUT("allocation/{id}")
    Call<Allocation> update(@Path("id") int id, @Body Allocation allocation);

    @GET("allocation")
    Call<List<Allocation>> getAllAllocation();

    @GET("allocation/{id}")
    Call<Allocation> getAllocation(@Path("id") int id);

    @DELETE("allocation/{id}")
    Call<Allocation> delete(@Path("id") int id);
}

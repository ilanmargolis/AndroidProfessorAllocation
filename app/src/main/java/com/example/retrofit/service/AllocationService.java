package com.example.retrofit.service;

import com.example.retrofit.model.Allocation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AllocationService {

    @GET("allocation")
    Call<List<Allocation>> getAllAllocation();
}

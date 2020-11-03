package com.example.retrofit.service;

import com.example.retrofit.model.Departament;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DepartamentService {

    @POST("departament")
    Call<Departament> create(@Body Departament departament);

    @PUT("departament/{id}")
    Call<Departament> update(@Path("id") int id, @Body Departament departament);

    @GET("departament")
    Call<List<Departament>> getAllDepartaments();

    @GET("departament/{id}")
    Call<Departament> getDepartament(@Path("id") int id);

    @DELETE("departament/{id}")
    Call<Departament> delete(@Path("id") int id);
}

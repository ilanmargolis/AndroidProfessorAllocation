package com.example.retrofit.service;

import com.example.retrofit.model.Departament;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface DepartamentService {

    @POST
    Call<Departament> create(@Body Departament departament);

    @GET("departament")
    Call<List<Departament>> getAllDepartaments();

    @GET("departament")
    Call<Departament> getDepartament(@Query("id") long id);

    @DELETE("departament")
    Call<Departament> delete(@Query("id") long id);
}

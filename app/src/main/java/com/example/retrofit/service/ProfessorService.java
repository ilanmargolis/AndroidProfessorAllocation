package com.example.retrofit.service;

import com.example.retrofit.model.Professor;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProfessorService {

    @POST("professor")
    Call<Professor> create(@Body Professor professor);

    @PUT("professor/{id}")
    Call<Professor> update(@Path("id") int id, @Body Professor professor);

    @GET("professor")
    Call<List<Professor>> getAllProfessor();

    @GET("professor/{id}")
    Call<Professor> getProfessor(@Path("id") int id);

    @DELETE("professor/{id}")
    Call<Professor> delete(@Path("id") int id);

}

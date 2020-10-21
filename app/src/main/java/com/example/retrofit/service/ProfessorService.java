package com.example.retrofit.service;

import com.example.retrofit.model.Professor;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ProfessorService {

    @POST
    Call<Professor> create(@Body Professor professor);

    @GET("professor")
    Call<List<Professor>> getAllProfessor();

    @GET("professor")
    Call<Professor> getProfessor(@Query("id") long id);

    @DELETE("professor")
    Call<Professor> delete(@Query("id") long id);

}

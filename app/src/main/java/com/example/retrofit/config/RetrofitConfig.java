package com.example.retrofit.config;

import com.example.retrofit.model.Professor;
import com.example.retrofit.service.DepartamentService;
import com.example.retrofit.service.ProfessorService;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitConfig {

    private Retrofit retrofit;

    public RetrofitConfig() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://professor-allocation.herokuapp.com/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public ProfessorService getProfessorService() {
        return retrofit.create(ProfessorService.class);
    }

    public DepartamentService getDepartamentService() {
        return retrofit.create(DepartamentService.class);
    }
}

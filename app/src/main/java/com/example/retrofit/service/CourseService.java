package com.example.retrofit.service;

import com.example.retrofit.model.Course;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CourseService {

    @POST("course")
    Call<Course> create(@Body Course course);

    @PUT("course/{id}")
    Call<Course> update(@Path("id") int id, @Body Course course);

    @GET("course")
    Call<List<Course>> getAllCourse();

    @GET("course/{id}")
    Call<Course> getCourse(@Path("id") int id);

    @DELETE("course/{id}")
    Call<Course> delete(@Path("id") int id);
}

package com.example.retrofit.service;

import com.example.retrofit.model.Course;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CourseService {

    @GET("course")
    Call<List<Course>> getAllCourse();
}

package com.example.retrofit.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.retrofit.model.Course;

import java.util.List;

@Dao
public interface CourseDao {

    @Query("SELECT * FROM Course")
    public List<Course> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertAll(List<Course> courseList);
}

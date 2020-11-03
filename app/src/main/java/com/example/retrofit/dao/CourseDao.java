package com.example.retrofit.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.retrofit.model.Course;

import java.util.List;

@Dao
public interface CourseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAll(List<Course> courseList);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert(Course course);

    @Update
    public void update(Course course);

    @Query("SELECT * FROM Course")
    public List<Course> getAll();

    @Query("SELECT * FROM Course WHERE server_id = :id")
    public Course getCourse(int id);

    @Delete
    public void delete(Course course);
}

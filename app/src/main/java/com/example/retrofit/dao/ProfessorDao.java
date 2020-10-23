package com.example.retrofit.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.retrofit.model.Professor;

import java.util.List;

@Dao
public interface ProfessorDao {

    @Query("SELECT * FROM Professor")
    public List<Professor> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertAll(List<Professor> professorList);
}

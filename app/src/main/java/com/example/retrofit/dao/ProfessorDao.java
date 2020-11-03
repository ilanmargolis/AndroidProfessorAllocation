package com.example.retrofit.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.retrofit.model.Professor;

import java.util.List;

@Dao
public interface ProfessorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAll(List<Professor> professorList);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert(Professor professor);

    @Update
    public void update(Professor professor);

    @Query("SELECT * FROM Professor")
    public List<Professor> getAll();

    @Query("SELECT * FROM professor WHERE server_id = :id")
    public Professor getProfessor(int id);

    @Delete
    public void delete(Professor professor);
}

package com.example.retrofit.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.retrofit.model.Departament;

import java.util.List;

@Dao
public interface DepartamentDao {

    @Query("SELECT * FROM Departament")
    public List<Departament> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertAll(List<Departament> departamentList);
}

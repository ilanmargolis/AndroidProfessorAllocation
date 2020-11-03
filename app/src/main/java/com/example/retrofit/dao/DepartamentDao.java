package com.example.retrofit.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.retrofit.model.Departament;

import java.util.List;

@Dao
public interface DepartamentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAll(List<Departament> departamentList);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert(Departament departament);

    @Update
    public void update(Departament departament);

    @Query("SELECT * FROM Departament")
    public List<Departament> getAll();

    @Query("SELECT * FROM Departament WHERE server_id = :id")
    public Departament getDepartament(int id);

    @Delete
    public void delete(Departament departament);
}

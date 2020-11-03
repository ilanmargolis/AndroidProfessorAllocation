package com.example.retrofit.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.retrofit.model.Allocation;

import java.util.List;

@Dao
public interface AllocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAll(List<Allocation> allocationList);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert(Allocation allocation);

    @Update
    public void update(Allocation allocation);

    @Query("SELECT * FROM Allocation")
    public List<Allocation> getAll();

    @Query("SELECT * FROM Allocation WHERE server_id = :id")
    public Allocation getAllocation(int id);

    @Delete
    public void delete(Allocation allocation);
}

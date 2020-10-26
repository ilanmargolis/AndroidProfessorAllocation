package com.example.retrofit.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.retrofit.model.Allocation;

import java.util.List;

@Dao
public interface AllocationDao {

    @Query("SELECT * FROM Allocation")
    public List<Allocation> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertAll(List<Allocation> courseList);
}

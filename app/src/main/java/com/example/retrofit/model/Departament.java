package com.example.retrofit.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Departament")
public class Departament {

    @PrimaryKey(autoGenerate = true)
    private int local_id;
    @ColumnInfo(name = "server_id")
    private int id;
    private String name;

    public Departament() {
    }

    public Departament(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getLocal_id() {
        return local_id;
    }

    public void setLocal_id(int local_id) {
        this.local_id = local_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
